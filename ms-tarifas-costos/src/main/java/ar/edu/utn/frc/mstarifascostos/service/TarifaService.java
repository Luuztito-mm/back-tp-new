package ar.edu.utn.frc.mstarifascostos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.utn.frc.mstarifascostos.model.Tarifa;
import ar.edu.utn.frc.mstarifascostos.repository.TarifaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TarifaService {

    private final TarifaRepository tarifaRepository;

    public List<Tarifa> listar() {
        return tarifaRepository.findAll();
    }

    public Tarifa guardar(Tarifa tarifa) {
        return tarifaRepository.save(tarifa);
    }

    public void eliminar(Long id) {
        tarifaRepository.deleteById(id);
    }

     // Calcular costo total según distancia
    public double calcularCosto(Long id, double distanciaKm) {
        Tarifa tarifa = tarifaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarifa no encontrada con id " + id));

        return tarifa.getCostoFijo() + tarifa.getCostoKm() * distanciaKm;
}
}