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

    @GetMapping
    public List<Camion> listar(@RequestParam(name = "disponible", required = false) Boolean disponible) {
        if (disponible != null) {
            return repo.findByDisponible(disponible);
        }
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Camion> obtener(@PathVariable Integer id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/dominio/{dominio}")
    public ResponseEntity<Camion> porDominio(@PathVariable String dominio) {
        return repo.findByDominio(dominio)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Camion> crear(@RequestBody Camion camion) {
        Camion guardado = repo.save(camion);
        return ResponseEntity.ok(guardado);
    }
}
