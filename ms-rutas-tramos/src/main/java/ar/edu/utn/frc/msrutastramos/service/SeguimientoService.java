package ar.edu.utn.frc.msrutastramos.service;

import ar.edu.utn.frc.msrutastramos.exception.BadRequestException;
import ar.edu.utn.frc.msrutastramos.model.EventoSeguimiento;
import ar.edu.utn.frc.msrutastramos.repository.EventoSeguimientoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SeguimientoService {

    private final EventoSeguimientoRepository eventoSeguimientoRepository;

    public SeguimientoService(EventoSeguimientoRepository eventoSeguimientoRepository) {
        this.eventoSeguimientoRepository = eventoSeguimientoRepository;
    }

    /**
     * Obtiene la "línea de tiempo" de eventos para una solicitud.
     * Si no hay eventos, simplemente devuelve una lista vacía.
     */
    public List<EventoSeguimiento> obtenerEventosPorSolicitud(Long solicitudId) {

        if (solicitudId == null || solicitudId <= 0) {
            throw new BadRequestException("El 'solicitudId' es obligatorio y debe ser > 0.");
        }

        return eventoSeguimientoRepository.findBySolicitudIdOrderByFechaHoraAsc(solicitudId);
    }

    /**
     * Registra un nuevo evento de seguimiento.
     * Pensado para que otros micros puedan ir agregando eventos.
     */
    public EventoSeguimiento registrarEvento(EventoSeguimiento evento) {

        if (evento.getSolicitudId() == null || evento.getSolicitudId() <= 0) {
            throw new BadRequestException("El campo solicitudId es obligatorio y debe ser > 0.");
        }
        if (evento.getEstado() == null || evento.getEstado().isBlank()) {
            throw new BadRequestException("El campo estado es obligatorio.");
        }

        // Si no viene fecha_hora, usamos ahora
        if (evento.getFechaHora() == null) {
            evento.setFechaHora(LocalDateTime.now());
        }

        return eventoSeguimientoRepository.save(evento);
    }
}
