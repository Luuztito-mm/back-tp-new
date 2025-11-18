package ar.edu.utn.frc.msrutastramos.service;

import ar.edu.utn.frc.msrutastramos.exception.BadRequestException;
import ar.edu.utn.frc.msrutastramos.model.dto.DistanciaDTO;
import ar.edu.utn.frc.msrutastramos.model.dto.OsrmRouteResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;

@Service
public class OsrmClient {

    private final RestTemplate restTemplate;
    private final String osrmBaseUrl;

    public OsrmClient(RestTemplate restTemplate,
                      @Value("${app.osrm.url}") String osrmBaseUrl) {
        this.restTemplate = restTemplate;
        this.osrmBaseUrl = osrmBaseUrl;
    }

    /**
     * Llama al servicio OSRM /route/v1/driving para calcular
     * distancia y duración entre dos coordenadas.
     *
     * @param origenLat   latitud del origen
     * @param origenLon   longitud del origen
     * @param destinoLat  latitud del destino
     * @param destinoLon  longitud del destino
     * @return DistanciaDTO con distancia en metros y duración en segundos
     */
    public DistanciaDTO calcularDistancia(double origenLat,
                                          double origenLon,
                                          double destinoLat,
                                          double destinoLon) {

        // OSRM espera long,lat
        String coordenadas = String.format(Locale.US,
                "%f,%f;%f,%f",
                origenLon, origenLat,
                destinoLon, destinoLat);

        String url = osrmBaseUrl + "/route/v1/driving/" + coordenadas + "?overview=false";

        try {
            OsrmRouteResponse response =
                    restTemplate.getForObject(url, OsrmRouteResponse.class);

            if (response == null
                    || response.getRoutes() == null
                    || response.getRoutes().isEmpty()) {
                throw new BadRequestException("Respuesta vacía desde OSRM al calcular la ruta.");
            }

            OsrmRouteResponse.Route route = response.getRoutes().get(0);

            return new DistanciaDTO(route.getDistance(), route.getDuration());

        } catch (RestClientException e) {
            throw new BadRequestException(
                    "No se pudo calcular la ruta en OSRM: " + e.getMessage());
        }
    }
}
