package ar.edu.utn.frc.mstarifascostos.controller;

import ar.edu.utn.frc.mstarifascostos.model.dto.CalcularTramoRequest;
import ar.edu.utn.frc.mstarifascostos.model.dto.CalcularTramoResponse;
import ar.edu.utn.frc.mstarifascostos.service.TarifaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CalculoTramosController {

    private final TarifaService tarifaService;

    /**
     * Endpoint documentado:
     * POST /calcular-tramo
     */
    @PostMapping("/calcular-tramo")
    public ResponseEntity<CalcularTramoResponse> calcularTramo(
            @RequestBody CalcularTramoRequest request) {

        CalcularTramoResponse response = tarifaService.calcularTramo(request);
        return ResponseEntity.ok(response);
    }
}
