package ar.edu.utn.frc.mstarifascostos.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalcularTramoResponse {

    private double distanciaKm;
    private long duracionEstMin;
    private TarifaBasicaDTO tarifaAplicada;
    private double costoAproximado;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TarifaBasicaDTO {
        private Long id;
        private String nombre;
    }
}
