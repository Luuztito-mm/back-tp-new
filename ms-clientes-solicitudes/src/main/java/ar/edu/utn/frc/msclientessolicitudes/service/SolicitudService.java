package ar.edu.utn.frc.msclientessolicitudes.service;

import ar.edu.utn.frc.msclientessolicitudes.model.Solicitud;
import ar.edu.utn.frc.msclientessolicitudes.repository.SolicitudRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SolicitudService {

    private final SolicitudRepository solicitudRepository;

    public SolicitudService(SolicitudRepository solicitudRepository) {
        this.solicitudRepository = solicitudRepository;
    }

    public List<Solicitud> listarTodas() {
        return solicitudRepository.findAll();
    }

    // este es el que usa el controller
    public Solicitud crearDesdeIds(Long clienteId, Long contenedorId) {
        Solicitud s = new Solicitud();
        s.setFechaCreacion(LocalDateTime.now());
        s.setClienteId(clienteId);
        s.setContenedorId(contenedorId);
        s.setEstado("PENDIENTE");
        // si querés más adelante calculás costo/tiempo
        return solicitudRepository.save(s);
    }
}
