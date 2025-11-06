package ar.edu.utn.frc.msclientessolicitudes.dto;

import lombok.Data;

@Data
public class NuevoEventoSeguimientoDTO {

    private Long solicitudId;     // obligatorio
    private String estado;        // obligatorio
    private String ubicacionTexto;
    private Long depositoId;
}
