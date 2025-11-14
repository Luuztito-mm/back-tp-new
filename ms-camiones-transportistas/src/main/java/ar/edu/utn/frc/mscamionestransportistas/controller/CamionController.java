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
    public List<Camion> listar(
        @RequestParam(name = "disponible", required = false) Boolean disponible
    ) {
        if (disponible != null) {
            return repo.findByDisponible(disponible);
        }
    return repo.findAll();
    }

    // GET /camiones/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Camion> buscarPorId(@PathVariable("id") Integer id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // GET /camiones/dominio/{dominio}
    @GetMapping("/dominio/{dominio}")
    public ResponseEntity<Camion> buscarPorDominio(@PathVariable("dominio") String dominio) {
        return repo.findByDominio(dominio)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /camiones
    @PostMapping
    public ResponseEntity<Camion> crear(@RequestBody Camion camion) {
        Camion guardado = repo.save(camion);
        return ResponseEntity.ok(guardado);
    }
}
