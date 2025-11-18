package ar.edu.utn.frc.msrutastramos.service;

import ar.edu.utn.frc.msrutastramos.exception.BadRequestException;
import ar.edu.utn.frc.msrutastramos.exception.ResourceNotFoundException;
import ar.edu.utn.frc.msrutastramos.model.Tramo;
import ar.edu.utn.frc.msrutastramos.repository.TramoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TramoService {

    private final TramoRepository tramoRepository;

    public TramoService(TramoRepository tramoRepository) {
        this.tramoRepository = tramoRepository;
    }

    // ------------------------------------------------------------------
    // LISTADOS
    // ------------------------------------------------------------------

    // Versión sin filtros (por si en algún lado la seguís usando)
    public List<Tramo> listar() {
        return listar(null, null);
    }

    // Versión con filtros rutaId / estado para el endpoint GET /tramos
    public List<Tramo> listar(Long rutaId, String estado) {
        boolean tieneRuta = rutaId != null;
        boolean tieneEstado = estado != null && !estado.isBlank();

        if (tieneRuta && tieneEstado) {
            return tramoRepository.findByRutaIdAndEstadoIgnoreCase(rutaId, estado);
        } else if (tieneRuta) {
            return tramoRepository.findByRutaId(rutaId);
        } else if (tieneEstado) {
            return tramoRepository.findByEstadoIgnoreCase(estado);
        } else {
            return tramoRepository.findAll();
        }
    }

    // ------------------------------------------------------------------
    // CREACIÓN
    // ------------------------------------------------------------------

    /**
     * Crea un tramo nuevo siempre con estado PENDIENTE (si no viene seteado)
     * y tipo NORMAL (si no viene seteado).
     * Valida que rutaId, origenDepositoId y destinoDepositoId no sean nulos.
     */
    public Tramo crear(Tramo tramo) {

        if (tramo.getRutaId() == null) {
            throw new BadRequestException("El campo rutaId es obligatorio.");
        }

        if (tramo.getOrigenDepositoId() == null || tramo.getDestinoDepositoId() == null) {
            throw new BadRequestException("Los campos origenDepositoId y destinoDepositoId son obligatorios.");
        }

        // Valor por defecto para estado y tipo
        if (tramo.getEstado() == null) {
            tramo.setEstado("PENDIENTE");
        }

        if (tramo.getTipo() == null) {
            tramo.setTipo("NORMAL");
        }

        return tramoRepository.save(tramo);
    }

    // ------------------------------------------------------------------
    // REGLAS DE NEGOCIO
    // ------------------------------------------------------------------

    /**
     * Asigna un camión a un tramo que aún no tiene camionId.
     */
    public Tramo asignarCamion(Long id, Long camionId) {
        Tramo tramo = tramoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe un tramo con id=" + id));

        if (tramo.getCamionId() != null) {
            throw new BadRequestException("El tramo con id=" + id + " ya tiene un camión asignado.");
        }

        tramo.setCamionId(camionId);
        // si quisieras, acá podrías cambiar estado a "ASIGNADO"
        return tramoRepository.save(tramo);
    }

    /**
     * Marca el tramo como EN_PROCESO y setea inicioReal con la fecha/hora actual.
     */
    public Tramo iniciar(Long id) {
        Tramo tramo = tramoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe un tramo con id=" + id));

        if (!tramo.puedeIniciarse()) {
            throw new BadRequestException("El tramo con id=" + id +
                    " no puede iniciarse porque su estado actual es: " + tramo.getEstado());
        }

        tramo.setEstado("EN_PROCESO");
        tramo.setInicioReal(LocalDateTime.now());
        return tramoRepository.save(tramo);
    }

    /**
     * Marca el tramo como FINALIZADO y setea finReal con la fecha/hora actual.
     */
    public Tramo finalizar(Long id) {
        Tramo tramo = tramoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe un tramo con id=" + id));

        if (!tramo.puedeFinalizarse()) {
            throw new BadRequestException("El tramo con id=" + id +
                    " no puede finalizarse porque su estado actual es: " + tramo.getEstado());
        }

        tramo.setEstado("FINALIZADO");
        tramo.setFinReal(LocalDateTime.now());
        return tramoRepository.save(tramo);
    }

    /**
     * Permite guardar el costo REAL del tramo (por ejemplo, calculado en ms-tarifas-costos).
     */
    public Tramo actualizarCostoReal(Long id, BigDecimal costoReal) {

        if (costoReal == null) {
            throw new BadRequestException("El costoReal es obligatorio.");
        }

        if (costoReal.compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("El costoReal no puede ser negativo.");
        }

        Tramo tramo = tramoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe un tramo con id=" + id));

        tramo.setCostoReal(costoReal);
        return tramoRepository.save(tramo);
    }
}
