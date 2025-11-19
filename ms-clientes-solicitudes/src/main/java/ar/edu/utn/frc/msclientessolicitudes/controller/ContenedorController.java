package ar.edu.utn.frc.msclientessolicitudes.controller;

import ar.edu.utn.frc.msclientessolicitudes.model.Contenedor;
import ar.edu.utn.frc.msclientessolicitudes.service.ContenedorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contenedores")
public class ContenedorController {

    private final ContenedorService contenedorService;

    public ContenedorController(ContenedorService contenedorService) {
        this.contenedorService = contenedorService;
    }

    @GetMapping
    public ResponseEntity<List<Contenedor>> listarContenedores(
            @RequestParam(name = "estado", required = false) String estado,
            @RequestParam(name = "clienteId", required = false) Long clienteId
    ) {
        List<Contenedor> contenedores = contenedorService.obtenerContenedores(estado, clienteId);
        return ResponseEntity.ok(contenedores);
    }


    @PostMapping
    public ResponseEntity<Contenedor> crearContenedor(@RequestBody Contenedor contenedor) {
        Contenedor creado = contenedorService.crearContenedor(contenedor);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }
}
