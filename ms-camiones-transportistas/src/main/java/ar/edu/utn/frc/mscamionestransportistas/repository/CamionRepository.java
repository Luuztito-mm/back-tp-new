package ar.edu.utn.frc.mscamionestransportistas.repository;

import ar.edu.utn.frc.mscamionestransportistas.model.Camion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CamionRepository extends JpaRepository<Camion, Integer> {
    List<Camion> findByDisponible(Boolean disponible);
    Optional<Camion> findByDominio(String dominio);
}
