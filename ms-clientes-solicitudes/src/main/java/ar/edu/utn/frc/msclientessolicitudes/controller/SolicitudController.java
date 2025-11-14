package ar.edu.utn.frc.msclientessolicitudes.controller;

import ar.edu.utn.frc.msclientessolicitudes.dto.NuevaSolicitudDTO;
import ar.edu.utn.frc.msclientessolicitudes.model.Solicitud;
import ar.edu.utn.frc.msclientessolicitudes.service.SolicitudService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/solicitudes")
public class SolicitudController {

    private final SolicitudService solicitudService;

    public SolicitudController(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    @GetMapping
    public List<Solicitud> getAll() {
        return solicitudService.listarTodas();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Solicitud crear(@RequestBody NuevaSolicitudDTO dto) {
        return solicitudService.crearDesdeIds(dto.getClienteId(), dto.getContenedorId());
    }

    @GetMapping("/{id}")
    public Solicitud getById(@PathVariable("id") Long id) {
        return solicitudService.buscarPorId(id);
    }
}
