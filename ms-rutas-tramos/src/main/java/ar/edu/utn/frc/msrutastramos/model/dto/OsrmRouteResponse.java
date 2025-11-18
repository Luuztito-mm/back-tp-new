package ar.edu.utn.frc.msrutastramos.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO para mapear la respuesta JSON de OSRM /route/v1/driving.
 *
 * Ejemplo:
 * {
 *   "routes": [
 *     { "distance": 351000.3, "duration": 15300.7, ... }
 *   ],
 *   "code": "Ok"
 * }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OsrmRouteResponse {

    private List<Route> routes;
    private String code;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Route {
        private double distance;  // metros
        private double duration;  // segundos
    }
}
