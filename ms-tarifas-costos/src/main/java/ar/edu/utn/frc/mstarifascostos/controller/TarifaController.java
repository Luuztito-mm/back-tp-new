package ar.edu.utn.frc.mstarifascostos.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.edu.utn.frc.mstarifascostos.model.Tarifa;
import ar.edu.utn.frc.mstarifascostos.service.TarifaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tarifas")
@RequiredArgsConstructor
public class TarifaController {

    private final TarifaService tarifaService;

    // ================= CRUD =================

    @GetMapping
    public List<Tarifa> listar() {
        return tarifaService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarifa> obtenerPorId(@PathVariable("id") Long id) {
        Tarifa tarifa = tarifaService.buscarPorId(id);
        if (tarifa == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tarifa);
    }

    @PostMapping
    public ResponseEntity<Tarifa> crear(@RequestBody Tarifa nuevaTarifa) {
        Tarifa guardada = tarifaService.guardar(nuevaTarifa);
        return ResponseEntity.ok(guardada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarifa> actualizar(@PathVariable("id") Long id,
                                             @RequestBody Tarifa datosActualizados) {

        Tarifa existente = tarifaService.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }

        existente.setNombre(datosActualizados.getNombre());
        existente.setRangoPesoMin(datosActualizados.getRangoPesoMin());
        existente.setRangoPesoMax(datosActualizados.getRangoPesoMax());
        existente.setRangoVolumenMin(datosActualizados.getRangoVolumenMin());
        existente.setRangoVolumenMax(datosActualizados.getRangoVolumenMax());
        existente.setCostoKmBase(datosActualizados.getCostoKmBase());
        existente.setCargoGestionPorTramo(datosActualizados.getCargoGestionPorTramo());

        Tarifa actualizada = tarifaService.guardar(existente);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long id) {
        Tarifa existente = tarifaService.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }

        tarifaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ================= CÁLCULO DE COSTO CON DISTANCIA (YA EXISTENTE) =================

    /**
     * Endpoint pensado para que lo llame ms-rutas-tramos (cuando ya conoce la distancia).
     *
     * Ejemplo:
     *   GET /tarifas/1/calcular?distanciaKm=350.5&cantidadTramos=3
     */
    @GetMapping("/{id}/calcular")
    public ResponseEntity<Double> calcularCosto(
            @PathVariable("id") Long id,
            @RequestParam("distanciaKm") double distanciaKm,
            @RequestParam(name = "cantidadTramos", defaultValue = "1") int cantidadTramos) {

        try {
            double total = tarifaService.calcularCosto(id, distanciaKm, cantidadTramos);
            return ResponseEntity.ok(total);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // ============ NUEVO: CÁLCULO DE COSTO USANDO OSRM DIRECTAMENTE ============

    /**
     * Variante que recibe coordenadas de origen y destino y deja que
     * este microservicio consulte OSRM para obtener la distancia.
     *
     * Ejemplo:
     *   GET /tarifas/1/calcular-coordenadas
     *       ?origenLat=-31.4135&origenLon=-64.18105
     *       &destinoLat=-32.9471&destinoLon=-60.6985
     *       &cantidadTramos=3
     */
    @GetMapping("/{id}/calcular-coordenadas")
    public ResponseEntity<Double> calcularCostoConCoordenadas(
            @PathVariable("id") Long id,
            @RequestParam("origenLat") double origenLat,
            @RequestParam("origenLon") double origenLon,
            @RequestParam("destinoLat") double destinoLat,
            @RequestParam("destinoLon") double destinoLon,
            @RequestParam(name = "cantidadTramos", defaultValue = "1") int cantidadTramos) {

        try {
            double total = tarifaService.calcularCostoConCoordenadas(
                    id, origenLat, origenLon, destinoLat, destinoLon, cantidadTramos);
            return ResponseEntity.ok(total);
        } catch (RuntimeException ex) {
            // tarifa no encontrada u otro problema de negocio
            return ResponseEntity.notFound().build();
        }
    }
}
