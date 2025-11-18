package ar.edu.utn.frc.msrutastramos.controller;

import ar.edu.utn.frc.msrutastramos.model.EventoSeguimiento;
import ar.edu.utn.frc.msrutastramos.service.SeguimientoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seguimiento")
public class SeguimientoController {

    private final SeguimientoService seguimientoService;

    public SeguimientoController(SeguimientoService seguimientoService) {
        this.seguimientoService = seguimientoService;
    }

    /**
     * GET /seguimiento/{solicitudId}
     *
     * Devuelve la lista de eventos de seguimiento asociados a la solicitud
     * indicada, ordenados cronológicamente.
     *
     * Ejemplo:
     *   GET /seguimiento/10
     */
    @GetMapping("/{solicitudId}")
    public ResponseEntity<List<EventoSeguimiento>> obtenerEventos(
            @PathVariable("solicitudId") Long solicitudId) {

        List<EventoSeguimiento> eventos =
                seguimientoService.obtenerEventosPorSolicitud(solicitudId);

        return ResponseEntity.ok(eventos);
    }

    /**
     * POST /seguimiento
     *
     * Permite registrar un nuevo evento de seguimiento.
     * Se espera un JSON con al menos:
     *  - solicitudId
     *  - estado
     * Opcionalmente:
     *  - fechaHora (si no viene, se usa la fecha/hora actual)
     *  - ubicacionTexto
     *  - depositoId
     */
    @PostMapping
    public ResponseEntity<EventoSeguimiento> registrarEvento(
            @RequestBody EventoSeguimiento evento) {

        EventoSeguimiento creado = seguimientoService.registrarEvento(evento);
        return ResponseEntity.ok(creado);
    }
}
