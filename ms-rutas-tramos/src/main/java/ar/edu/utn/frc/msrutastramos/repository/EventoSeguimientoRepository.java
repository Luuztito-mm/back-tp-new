package ar.edu.utn.frc.msrutastramos.repository;

import ar.edu.utn.frc.msrutastramos.model.EventoSeguimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoSeguimientoRepository extends JpaRepository<EventoSeguimiento, Long> {

    /**
     * Devuelve todos los eventos asociados a una solicitud,
     * ordenados por fecha/hora ascendente (línea de tiempo).
     */
    List<EventoSeguimiento> findBySolicitudIdOrderByFechaHoraAsc(Long solicitudId);
}
