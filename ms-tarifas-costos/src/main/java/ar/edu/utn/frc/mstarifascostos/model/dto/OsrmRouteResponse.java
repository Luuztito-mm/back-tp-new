package ar.edu.utn.frc.mstarifascostos.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Mapeo mínimo de la respuesta de OSRM /route/v1/driving.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OsrmRouteResponse {

    private List<Route> routes;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Route {
        private List<Leg> legs;
        private double distance;
        private double duration;
        private String weight_name;
        private double weight;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Leg {
        private List<Object> steps;
        private double distance;
        private double duration;
        private String summary;
    }
}
