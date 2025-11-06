package ar.edu.utn.frc.msclientessolicitudes.repository;

import ar.edu.utn.frc.msclientessolicitudes.model.EventoSeguimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoSeguimientoRepository extends JpaRepository<EventoSeguimiento, Long> {

    List<EventoSeguimiento> findBySolicitudIdOrderByFechaHoraAsc(Long solicitudId);
}
