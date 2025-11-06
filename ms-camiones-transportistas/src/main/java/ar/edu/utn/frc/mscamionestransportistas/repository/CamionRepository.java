package ar.edu.utn.frc.mscamionestransportistas.repository;

import ar.edu.utn.frc.mscamionestransportistas.model.Camion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CamionRepository extends JpaRepository<Camion, String> {

    List<Camion> findByDisponible(Boolean disponible);
}
