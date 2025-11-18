package ar.edu.utn.frc.msrutastramos.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa el resultado de un cálculo de ruta
 * (puede venir de OSRM u otro servicio externo de ruteo):
 * - distanciaMetros: distancia en metros (double)
 * - duracionSegundos: duración en segundos (double)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistanciaDTO {

    private double distanciaMetros;
    private double duracionSegundos;
}
