package ar.edu.utn.frc.msrutastramos.controller;

import ar.edu.utn.frc.msrutastramos.model.Tramo;
import ar.edu.utn.frc.msrutastramos.service.TramoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/tramos")
public class TramoController {

    private final TramoService tramoService;

    public TramoController(TramoService tramoService) {
        this.tramoService = tramoService;
    }

    // ------------------------------------------------------------
    // GET /tramos?rutaId&estado  (ambos filtros opcionales)
    // ------------------------------------------------------------
    @GetMapping
    public ResponseEntity<List<Tramo>> listar(
            @RequestParam(name = "rutaId", required = false) Long rutaId,
            @RequestParam(name = "estado", required = false) String estado) {

        List<Tramo> tramos = tramoService.listar(rutaId, estado);
        return ResponseEntity.ok(tramos);
    }

    // ------------------------------------------------------------
    // POST /tramos  (crear tramo)
    // ------------------------------------------------------------
    @PostMapping
    public ResponseEntity<Tramo> crear(@RequestBody Tramo tramo) {
        Tramo creado = tramoService.crear(tramo);
        return ResponseEntity.ok(creado);
    }

    // ------------------------------------------------------------
    // POST /tramos/{id}/asignar-camion?camionId=123
    // ------------------------------------------------------------
    @PostMapping("/{id}/asignar-camion")
    public ResponseEntity<Tramo> asignarCamion(@PathVariable("id") Long id,
                                               @RequestParam("camionId") Long camionId) {
        Tramo actualizado = tramoService.asignarCamion(id, camionId);
        return ResponseEntity.ok(actualizado);
    }

    // ------------------------------------------------------------
    // POST /tramos/{id}/iniciar
    // ------------------------------------------------------------
    @PostMapping("/{id}/iniciar")
    public ResponseEntity<Tramo> iniciar(@PathVariable("id") Long id) {
        Tramo actualizado = tramoService.iniciar(id);
        return ResponseEntity.ok(actualizado);
    }

    // ------------------------------------------------------------
    // POST /tramos/{id}/finalizar
    // ------------------------------------------------------------
    @PostMapping("/{id}/finalizar")
    public ResponseEntity<Tramo> finalizar(@PathVariable("id") Long id) {
        Tramo actualizado = tramoService.finalizar(id);
        return ResponseEntity.ok(actualizado);
    }

    // ------------------------------------------------------------
    // PUT /tramos/{id}/costo-real?costoReal=15000.50
    // ------------------------------------------------------------
    @PutMapping("/{id}/costo-real")
    public ResponseEntity<Tramo> actualizarCostoReal(@PathVariable("id") Long id,
                                                     @RequestParam("costoReal") BigDecimal costoReal) {
        Tramo actualizado = tramoService.actualizarCostoReal(id, costoReal);
        return ResponseEntity.ok(actualizado);
    }
}
