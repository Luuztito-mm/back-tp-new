package ar.edu.utn.frc.msrutastramos.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.msrutastramos.model.dto.RutaCalculadaDTO;
import ar.edu.utn.frc.msrutastramos.model.Tramo;
import ar.edu.utn.frc.msrutastramos.repository.TramoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TramoService {

    private final TramoRepository tramoRepository;

    // ---------- CRUD que ya tenías ----------
    public List<Tramo> listar() { return tramoRepository.findAll(); }

    public Optional<Tramo> buscarPorId(Long id) { return tramoRepository.findById(id); }

    public Tramo guardar(Tramo tramo) { return tramoRepository.save(tramo); }

    public void eliminar(Long id) { tramoRepository.deleteById(id); }

    public double calcularDistancia(String origen, String destino) {
        return tramoRepository.findByOrigenAndDestino(origen, destino)
                .stream().mapToDouble(Tramo::getDistanciaKm).sum();
    }

    // ---------- NUEVO: calcular mejor ruta ----------
    public RutaCalculadaDTO calcularMejorRuta(String origen, String destino) {
        // 1) Si hay tramo directo, lo usamos
        List<Tramo> directos = tramoRepository.findByOrigenAndDestino(origen, destino);
        if (!directos.isEmpty()) {
            double dist = directos.stream().mapToDouble(Tramo::getDistanciaKm).sum();
            double dur  = directos.stream().mapToDouble(Tramo::getDuracionHs).sum();
            return new RutaCalculadaDTO(directos, dist, dur);
        }

        // 2) Construir un grafo en memoria a partir de todos los tramos
        List<Tramo> todos = tramoRepository.findAll();
        Map<String, List<Tramo>> adj = todos.stream()
                .collect(Collectors.groupingBy(Tramo::getOrigen));

        // 3) Dijkstra por distancia
        Map<String, Double> dist = new HashMap<>();
        Map<String, Tramo> prevEdge = new HashMap<>();
        PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingDouble(dist::get));

        // inicialización
        Set<String> nodos = new HashSet<>();
        todos.forEach(t -> { nodos.add(t.getOrigen()); nodos.add(t.getDestino()); });

        for (String n : nodos) dist.put(n, Double.POSITIVE_INFINITY);
        dist.put(origen, 0.0);
        pq.add(origen);

        while (!pq.isEmpty()) {
            String u = pq.poll();
            if (u.equals(destino)) break; // ya llegamos con la mínima

            for (Tramo e : adj.getOrDefault(u, Collections.emptyList())) {
                String v = e.getDestino();
                double alt = dist.get(u) + e.getDistanciaKm();
                if (alt < dist.getOrDefault(v, Double.POSITIVE_INFINITY)) {
                    dist.put(v, alt);
                    prevEdge.put(v, e);
                    // re-insertar v en la cola con nueva prioridad
                    pq.remove(v);
                    pq.add(v);
                }
            }
        }

        if (!prevEdge.containsKey(destino)) {
            // No hay camino posible con los tramos cargados
            return new RutaCalculadaDTO(Collections.emptyList(), 0, 0);
        }

        // 4) reconstruir camino desde destino hacia origen
        List<Tramo> camino = new ArrayList<>();
        String actual = destino;
        while (!actual.equals(origen)) {
            Tramo e = prevEdge.get(actual);
            camino.add(e);
            actual = e.getOrigen();
        }
        Collections.reverse(camino);

        double distanciaTotal = camino.stream().mapToDouble(Tramo::getDistanciaKm).sum();
        double duracionTotal  = camino.stream().mapToDouble(Tramo::getDuracionHs).sum();

        return new RutaCalculadaDTO(camino, distanciaTotal, duracionTotal);
    }
}
