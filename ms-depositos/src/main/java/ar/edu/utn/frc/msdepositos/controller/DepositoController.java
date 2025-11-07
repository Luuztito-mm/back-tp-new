package ar.edu.utn.frc.msdepositos.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frc.msdepositos.model.Deposito;
import ar.edu.utn.frc.msdepositos.service.DepositoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/depositos")
@RequiredArgsConstructor
public class DepositoController {

    private final DepositoService depositoService;

    // Endpoint para versión del servicio
    @GetMapping("/info")
    public Map<String, String> version() {
        return Map.of(
            "service", "ms-depositos",
            "version", "0.0.1"
        );
    }

    // CRUD endpoints
    @GetMapping
    public List<Deposito> listar() {
        return depositoService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Deposito> obtenerPorId(@PathVariable Long id) {
        return depositoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Deposito> crear(@Valid @RequestBody Deposito deposito) {
        return ResponseEntity.ok(depositoService.guardar(deposito));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Deposito> actualizar(@PathVariable Long id,
                                               @Valid @RequestBody Deposito deposito) {
        return ResponseEntity.ok(depositoService.actualizar(id, deposito));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        depositoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
