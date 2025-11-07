package ar.edu.utn.frc.mstarifascostos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frc.mstarifascostos.model.Tarifa;
import ar.edu.utn.frc.mstarifascostos.repository.TarifaRepository;

@RestController
@RequestMapping("/api/tarifas")
public class TarifaController {

    @Autowired
    private TarifaRepository tarifaRepository;

    // Obtener todas las tarifas
    @GetMapping
    public List<Tarifa> listar() {
        return tarifaRepository.findAll();
    }

    // Obtener una tarifa por ID
    @GetMapping("/{id}")
    public ResponseEntity<Tarifa> obtenerPorId(@PathVariable Long id) {
        Tarifa tarifa = tarifaRepository.findById(id).orElse(null);
        if (tarifa == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tarifa);
    }

    // Crear una nueva tarifa
    @PostMapping
    public ResponseEntity<Tarifa> crear(@RequestBody Tarifa nuevaTarifa) {
        Tarifa guardada = tarifaRepository.save(nuevaTarifa);
        return ResponseEntity.ok(guardada);
    }

    // Actualizar una tarifa existente
    @PutMapping("/{id}")
    public ResponseEntity<Tarifa> actualizar(@PathVariable Long id, @RequestBody Tarifa datosActualizados) {
        return tarifaRepository.findById(id)
                .map(tarifa -> {
                    tarifa.setTipoServicio(datosActualizados.getTipoServicio());
                    tarifa.setCostoFijo(datosActualizados.getCostoFijo());
                    tarifa.setCostoKm(datosActualizados.getCostoKm());
                    Tarifa actualizada = tarifaRepository.save(tarifa);
                    return ResponseEntity.ok(actualizada);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar una tarifa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!tarifaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        tarifaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Calcular costo total de un viaje o tramo
    @GetMapping("/{id}/calcular")
    public ResponseEntity<Double> calcularCosto(
            @PathVariable Long id,
            @RequestParam double distanciaKm) {

        Tarifa tarifa = tarifaRepository.findById(id).orElse(null);
        if (tarifa == null) {
            return ResponseEntity.notFound().build();
        }

        double total = tarifa.getCostoFijo() + (tarifa.getCostoKm() * distanciaKm);
        return ResponseEntity.ok(total);
    }
}
