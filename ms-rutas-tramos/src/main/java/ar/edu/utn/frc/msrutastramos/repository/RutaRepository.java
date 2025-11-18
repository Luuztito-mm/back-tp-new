package ar.edu.utn.frc.msrutastramos.repository;

import ar.edu.utn.frc.msrutastramos.model.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RutaRepository extends JpaRepository<Ruta, Long> {
    // métodos de búsqueda extra si alguna vez los necesitas
}