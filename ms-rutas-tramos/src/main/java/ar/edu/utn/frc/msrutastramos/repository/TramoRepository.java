package ar.edu.utn.frc.msrutastramos.repository;

import ar.edu.utn.frc.msrutastramos.model.Tramo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TramoRepository extends JpaRepository<Tramo, Long> {

    // Filtros para GET /tramos?rutaId&estado
    List<Tramo> findByRutaId(Long rutaId);

    List<Tramo> findByEstadoIgnoreCase(String estado);

    List<Tramo> findByRutaIdAndEstadoIgnoreCase(Long rutaId, String estado);
}
