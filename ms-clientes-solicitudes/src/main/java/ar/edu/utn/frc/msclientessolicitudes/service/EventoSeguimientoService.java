package ar.edu.utn.frc.msclientessolicitudes.service;

import ar.edu.utn.frc.msclientessolicitudes.dto.NuevoEventoSeguimientoDTO;
import ar.edu.utn.frc.msclientessolicitudes.model.EventoSeguimiento;
import ar.edu.utn.frc.msclientessolicitudes.repository.EventoSeguimientoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventoSeguimientoService {

    private final EventoSeguimientoRepository repo;

    public EventoSeguimientoService(EventoSeguimientoRepository repo) {
        this.repo = repo;
    }

    public EventoSeguimiento crear(NuevoEventoSeguimientoDTO dto) {
        EventoSeguimiento ev = new EventoSeguimiento();
        ev.setSolicitudId(dto.getSolicitudId());
        ev.setEstado(dto.getEstado());
        ev.setUbicacionTexto(dto.getUbicacionTexto());
        ev.setDepositoId(dto.getDepositoId());
        ev.setFechaHora(LocalDateTime.now());   // <- lo ponemos nosotros
        return repo.save(ev);
    }

    public List<EventoSeguimiento> porSolicitud(Long solicitudId) {
        return repo.findBySolicitudIdOrderByFechaHoraAsc(solicitudId);
    }
}
