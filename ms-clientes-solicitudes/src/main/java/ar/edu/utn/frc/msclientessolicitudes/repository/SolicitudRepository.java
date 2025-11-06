package ar.edu.utn.frc.msclientessolicitudes.repository;

import ar.edu.utn.frc.msclientessolicitudes.model.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
}
