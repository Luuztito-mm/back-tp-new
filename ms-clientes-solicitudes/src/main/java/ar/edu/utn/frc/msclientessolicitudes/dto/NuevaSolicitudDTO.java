package ar.edu.utn.frc.msclientessolicitudes.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NuevaSolicitudDTO {

    @NotNull
    private Long clienteId;

    @NotNull
    private Long contenedorId;
}
