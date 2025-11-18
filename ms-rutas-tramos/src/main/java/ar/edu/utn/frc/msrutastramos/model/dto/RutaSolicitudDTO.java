package ar.edu.utn.frc.msrutastramos.model.dto;

import lombok.Data;

/**
 * DTO para pedir el cálculo de una ruta entre origen y destino.
 * El formato lo decide tu API (direcciones o lat,long).
 */
@Data
public class RutaSolicitudDTO {

    private String origen;
    private String destino;
}
