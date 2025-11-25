package ar.edu.utn.frc.mstarifascostos.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalcularTramoRequest {

    private PuntoDTO origen;
    private PuntoDTO destino;
    private ContenedorDTO contenedor;
    private CamionDTO camion;
    private int estadiaDias;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PuntoDTO {
        private double lat;
        private double lon;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContenedorDTO {
        private double pesoKg;
        private double volumenM3;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CamionDTO {
        private double consumoLitrosKm;
        private double costoBaseKm;
    }
}
