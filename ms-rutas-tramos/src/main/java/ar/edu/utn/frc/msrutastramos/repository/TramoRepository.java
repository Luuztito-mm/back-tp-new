package ar.edu.utn.frc.msrutastramos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ar.edu.utn.frc.msrutastramos.model.Tramo;
import java.util.List;

public interface TramoRepository extends JpaRepository<Tramo, Long> {

    List<Tramo> findByOrigenAndDestino(String origen, String destino);

    List<Tramo> findByOrigen(String origen);

    @Query("SELECT t FROM Tramo t WHERE t.origen = :origen AND t.destino = :destino")
    List<Tramo> buscarDirecto(@Param("origen") String origen, @Param("destino") String destino);
}
