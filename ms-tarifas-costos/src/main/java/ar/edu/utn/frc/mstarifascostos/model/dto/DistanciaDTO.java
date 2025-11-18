package ar.edu.utn.frc.mstarifascostos.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa el resultado de un cálculo de ruta:
 * - distanciaMetros: distancia total en metros (double)
 * - duracionSegundos: duración total en segundos (double)
 *
 * Estos valores van a venir de OSRM.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistanciaDTO {

    private double distanciaMetros;
    private double duracionSegundos;
}
