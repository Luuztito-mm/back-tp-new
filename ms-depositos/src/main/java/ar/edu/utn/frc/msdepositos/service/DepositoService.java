package ar.edu.utn.frc.msdepositos.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ar.edu.utn.frc.msdepositos.model.Deposito;
import ar.edu.utn.frc.msdepositos.repository.DepositoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepositoService {

    private static final Logger logger = LoggerFactory.getLogger(DepositoService.class);
    private final DepositoRepository depositoRepository;

    public List<Deposito> listar() {
        logger.info("Obteniendo lista completa de depósitos");
        return depositoRepository.findAll();
    }

    public Optional<Deposito> buscarPorId(Long id) {
        logger.info("Buscando depósito con id: {}", id);
        return depositoRepository.findById(id);
    }

    public Deposito guardar(Deposito deposito) {
        logger.info("Guardando nuevo depósito: {}", deposito.getNombre());
        return depositoRepository.save(deposito);
    }

    public Deposito actualizar(Long id, Deposito nuevo) {
        logger.info("Actualizando depósito con id: {}", id);
        return depositoRepository.findById(id)
                .map(dep -> {
                    dep.setNombre(nuevo.getNombre());
                    dep.setDireccion(nuevo.getDireccion());
                    dep.setLat(nuevo.getLat());
                    dep.setLon(nuevo.getLon());
                    dep.setCostoEstadiaDiaria(nuevo.getCostoEstadiaDiaria());
                    logger.info("Depósito actualizado: {}", dep.getNombre());
                    return depositoRepository.save(dep);
                })
                .orElseThrow(() -> {
                    logger.error("No se encontró el depósito con id: {}", id);
                    return new RuntimeException("Depósito no encontrado");
                });
    }

    public void eliminar(Long id) {
        logger.warn("Eliminando depósito con id: {}", id);
        depositoRepository.deleteById(id);
    }
}
