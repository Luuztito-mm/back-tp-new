package ar.edu.utn.frc.msrutastramos.service;

import ar.edu.utn.frc.msrutastramos.exception.BadRequestException;
import ar.edu.utn.frc.msrutastramos.exception.ResourceNotFoundException;
import ar.edu.utn.frc.msrutastramos.model.Ruta;
import ar.edu.utn.frc.msrutastramos.model.dto.DistanciaDTO;
import ar.edu.utn.frc.msrutastramos.repository.RutaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RutaService {

    private final RutaRepository rutaRepository;
    private final RestTemplate restTemplate;
    private final OsrmClient osrmClient;   // 👈 NUEVO

    /**
     * URL base del micro ms-clientes-solicitudes.
     * Definida en application.properties:
     * app.ms-clientes-solicitudes.url=http://localhost:8081
     */
    @Value("${app.ms-clientes-solicitudes.url}")
    private String msClientesSolicitudesBaseUrl;

    public RutaService(RutaRepository rutaRepository,
                       RestTemplate restTemplate,
                       OsrmClient osrmClient) {   // 👈 NUEVO parámetro
        this.rutaRepository = rutaRepository;
        this.restTemplate = restTemplate;
        this.osrmClient = osrmClient;
    }

    // ==================== LÓGICA EXISTENTE ====================

    public Ruta crearRutaTentativa(Ruta nuevaRuta) {

        if (nuevaRuta == null) {
            throw new BadRequestException("El cuerpo de la solicitud no puede ser nulo.");
        }
        if (nuevaRuta.getSolicitudId() == null || nuevaRuta.getSolicitudId() <= 0) {
            throw new BadRequestException("El campo 'solicitudId' es obligatorio y debe ser > 0.");
        }
        if (nuevaRuta.getCantidadTramos() == null || nuevaRuta.getCantidadTramos() < 0) {
            throw new BadRequestException("El campo 'cantidadTramos' no puede ser negativo.");
        }
        if (nuevaRuta.getCantidadDepositos() == null || nuevaRuta.getCantidadDepositos() < 0) {
            throw new BadRequestException("El campo 'cantidadDepositos' no puede ser negativo.");
        }

        nuevaRuta.setAsignada(false);
        return rutaRepository.save(nuevaRuta);
    }

    public Ruta asignarRuta(Long id) {
        Ruta ruta = rutaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe la ruta con id=" + id));

        if (Boolean.TRUE.equals(ruta.getAsignada())) {
            throw new BadRequestException("La ruta con id=" + id + " ya está asignada.");
        }

        ruta.setAsignada(true);
        return rutaRepository.save(ruta);
    }

    public List<Ruta> listarRutas() {
        return rutaRepository.findAll();
    }

    public Ruta crearRutaParaSolicitud(Long solicitudId) {

        if (solicitudId == null || solicitudId <= 0) {
            throw new BadRequestException("El 'solicitudId' es obligatorio y debe ser > 0.");
        }

        validarSolicitudEnMsClientes(solicitudId);

        Ruta ruta = Ruta.builder()
                .solicitudId(solicitudId)
                .asignada(false)
                .cantidadTramos(0)
                .cantidadDepositos(0)
                .build();

        return rutaRepository.save(ruta);
    }

    private void validarSolicitudEnMsClientes(Long solicitudId) {
        String url = msClientesSolicitudesBaseUrl + "/solicitudes/" + solicitudId;

        try {
            ResponseEntity<Void> response = restTemplate.getForEntity(url, Void.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new BadRequestException(
                        "No se pudo validar la solicitud con id=" + solicitudId +
                                " en ms-clientes-solicitudes (status: " + response.getStatusCode() + ").");
            }

        } catch (HttpClientErrorException.NotFound ex) {
            throw new BadRequestException("No existe la solicitud con id=" + solicitudId + " en ms-clientes-solicitudes.");
        } catch (RestClientException ex) {
            throw new BadRequestException(
                    "Error al comunicarse con ms-clientes-solicitudes para validar la solicitud con id=" + solicitudId + ".");
        }
    }

    // ==================== LÓGICA NUEVA: OSRM ====================

    /**
     * Usa OsrmClient para calcular distancia y duración entre dos coordenadas.
     */
    public DistanciaDTO calcularDistanciaEntreCoordenadas(double origenLat,
                                                          double origenLon,
                                                          double destinoLat,
                                                          double destinoLon) {
        return osrmClient.calcularDistancia(origenLat, origenLon, destinoLat, destinoLon);
    }
}
