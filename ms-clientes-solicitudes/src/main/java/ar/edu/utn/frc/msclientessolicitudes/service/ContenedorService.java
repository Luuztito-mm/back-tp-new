package ar.edu.utn.frc.msclientessolicitudes.service;

import ar.edu.utn.frc.msclientessolicitudes.model.Contenedor;
import ar.edu.utn.frc.msclientessolicitudes.repository.ContenedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContenedorService {

    private final ContenedorRepository contenedorRepository;

    public ContenedorService(ContenedorRepository contenedorRepository) {
        this.contenedorRepository = contenedorRepository;
    }

    public List<Contenedor> obtenerContenedores(String estado, Long clienteId) {

        if (estado == null && clienteId == null) {
            return contenedorRepository.findAll();
        }

        if (estado != null && clienteId == null) {
            return contenedorRepository.findByEstado(estado);
        }

        if (estado == null && clienteId != null) {
            return contenedorRepository.findByClienteId(clienteId);
        }

        return contenedorRepository.findByEstadoAndClienteId(estado, clienteId);
    }

    public Contenedor crearContenedor(Contenedor contenedor) {
        return contenedorRepository.save(contenedor);
    }
}
