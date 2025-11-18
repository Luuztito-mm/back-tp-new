package ar.edu.utn.frc.mstarifascostos.service;

import ar.edu.utn.frc.mstarifascostos.model.dto.DistanciaDTO;
import ar.edu.utn.frc.mstarifascostos.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;

@Service
public class RutasTramosClient {

    private final RestTemplate restTemplate;
    private final String rutasTramosBaseUrl;

    public RutasTramosClient(RestTemplate restTemplate,
                             @Value("${app.ms-rutas-tramos.url}") String rutasTramosBaseUrl) {
        this.restTemplate = restTemplate;
        this.rutasTramosBaseUrl = rutasTramosBaseUrl;
    }

    /**
     * Llama a ms-rutas-tramos: GET
     * /rutas/distancia?origenLat=...&origenLon=...&destinoLat=...&destinoLon=...
     * y devuelve la distancia en metros y la duración en segundos.
     */
    public DistanciaDTO obtenerDistancia(double origenLat,
                                         double origenLon,
                                         double destinoLat,
                                         double destinoLon) {

        String url = String.format(
                Locale.US,
                "%s/rutas/distancia?origenLat=%f&origenLon=%f&destinoLat=%f&destinoLon=%f",
                rutasTramosBaseUrl,
                origenLat, origenLon,
                destinoLat, destinoLon
        );

        try {
            DistanciaDTO dto = restTemplate.getForObject(url, DistanciaDTO.class);

            if (dto == null) {
                throw new BadRequestException("Respuesta vacía desde ms-rutas-tramos al calcular la distancia.");
            }

            return dto;

        } catch (RestClientException e) {
            throw new BadRequestException(
                    "Error al llamar a ms-rutas-tramos para calcular la distancia: " + e.getMessage()
            );
        }
    }
}
