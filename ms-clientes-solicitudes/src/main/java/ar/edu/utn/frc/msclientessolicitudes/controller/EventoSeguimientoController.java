package ar.edu.utn.frc.msclientessolicitudes.controller;

import ar.edu.utn.frc.msclientessolicitudes.dto.NuevoEventoSeguimientoDTO;
import ar.edu.utn.frc.msclientessolicitudes.model.EventoSeguimiento;
import ar.edu.utn.frc.msclientessolicitudes.service.EventoSeguimientoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventos-seguimiento")
public class EventoSeguimientoController {

    private final EventoSeguimientoService service;

    public EventoSeguimientoController(EventoSeguimientoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventoSeguimiento crear(@RequestBody NuevoEventoSeguimientoDTO dto) {
        return service.crear(dto);
    }

    @GetMapping("/solicitud/{solicitudId}")
    public List<EventoSeguimiento> listar(@PathVariable Long solicitudId) {
        return service.porSolicitud(solicitudId);
    }
}