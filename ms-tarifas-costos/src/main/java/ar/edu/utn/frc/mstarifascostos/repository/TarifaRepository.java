package ar.edu.utn.frc.mstarifascostos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ar.edu.utn.frc.mstarifascostos.model.Tarifa;

public interface TarifaRepository extends JpaRepository<Tarifa, Long> {
}
