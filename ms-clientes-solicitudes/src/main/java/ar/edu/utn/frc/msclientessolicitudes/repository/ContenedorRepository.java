package ar.edu.utn.frc.msclientessolicitudes.repository;

import ar.edu.utn.frc.msclientessolicitudes.model.Contenedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContenedorRepository extends JpaRepository<Contenedor, Long> {

    // Filtro solo por estado
    List<Contenedor> findByEstado(String estado);

    // Filtro solo por id de cliente (asumiendo que Contenedor tiene Cliente cliente)
    List<Contenedor> findByClienteId(Long clienteId);

    // Filtro combinado
    List<Contenedor> findByEstadoAndClienteId(String estado, Long clienteId);
}
