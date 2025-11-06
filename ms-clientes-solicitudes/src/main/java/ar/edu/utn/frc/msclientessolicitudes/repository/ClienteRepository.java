package ar.edu.utn.frc.msclientessolicitudes.repository;

import ar.edu.utn.frc.msclientessolicitudes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByEmail(String email);
}
