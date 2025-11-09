package ar.edu.utn.frc.msrutastramos.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ar.edu.utn.frc.msrutastramos.model.Tramo;
import ar.edu.utn.frc.msrutastramos.service.TramoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/rutas")
@RequiredArgsConstructor
public class TramoController {

    private final TramoService tramoService;

    @GetMapping("/info")
    public Map<String, String> info() {
        return Map.of("service", "ms-rutas-tramos", "version", "1.0.0");
    }

    @GetMapping
    public List<Tramo> listar() {
        return tramoService.listar();
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<Tramo> obtener(@PathVariable Long id) {
        return tramoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tramo> crear(@RequestBody Tramo tramo) {
        return ResponseEntity.ok(tramoService.guardar(tramo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tramoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/calcular")
    public ResponseEntity<Double> calcularDistancia(
            @RequestParam String origen,
            @RequestParam String destino) {
        double total = tramoService.calcularDistancia(origen, destino);
        return ResponseEntity.ok(total);
    }

    // NUEVO: calcular mejor ruta entre orgen y destino
    @GetMapping("/mejor-camino")
    public ResponseEntity<?> mejorCamino(
            @RequestParam String origen,
            @RequestParam String destino) {
        var ruta = tramoService.calcularMejorRuta(origen, destino);
        if (ruta.getTramos().isEmpty()) {
            return ResponseEntity.status(404).body(
                Map.of("message", "No existe un camino entre " + origen + " y " + destino)
            );
        }
        return ResponseEntity.ok(ruta);
    }

}
