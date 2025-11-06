package ar.edu.utn.frc.mscamionestransportistas.controller;

import ar.edu.utn.frc.mscamionestransportistas.model.Camion;
import ar.edu.utn.frc.mscamionestransportistas.repository.CamionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/camiones")
public class CamionController {

    private final CamionRepository repo;

    public CamionController(CamionRepository repo) {
        this.repo = repo;
    }

    // GET /camiones?disponible=true
    @GetMapping
    public List<Camion> listar(@RequestParam(required = false) Boolean disponible) {
        if (disponible != null) {
            return repo.findByDisponible(disponible);
        }
        return repo.findAll();
    }

    // POST /camiones
    @PostMapping
    public ResponseEntity<Camion> crear(@RequestBody Camion camion) {
        Camion guardado = repo.save(camion);
        return ResponseEntity.ok(guardado);
    }

    // GET /camiones/{dominio}
    @GetMapping("/{dominio}")
    public ResponseEntity<Camion> obtener(@PathVariable String dominio) {
        return repo.findById(dominio)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
