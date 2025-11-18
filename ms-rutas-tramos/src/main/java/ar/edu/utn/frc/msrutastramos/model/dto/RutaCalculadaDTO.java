package ar.edu.utn.frc.msrutastramos.model.dto;

import java.util.List;

import ar.edu.utn.frc.msrutastramos.model.Tramo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RutaCalculadaDTO {

    private List<Tramo> tramos;
    private double distanciaTotal; // en km, por ejemplo
    private double duracionTotal;  // en minutos, por ejemplo
}
