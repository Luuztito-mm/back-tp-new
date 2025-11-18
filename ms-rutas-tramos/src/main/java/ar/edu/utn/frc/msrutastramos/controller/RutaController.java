package ar.edu.utn.frc.msrutastramos.controller;

import ar.edu.utn.frc.msrutastramos.model.Ruta;
import ar.edu.utn.frc.msrutastramos.model.dto.DistanciaDTO;
import ar.edu.utn.frc.msrutastramos.service.RutaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rutas")
public class RutaController {

    private final RutaService rutaService;

    public RutaController(RutaService rutaService) {
        this.rutaService = rutaService;
    }

    /**
     * POST /rutas
     * Crea una ruta tentativa a partir de los datos enviados en el body.
     */
    @PostMapping
    public ResponseEntity<Ruta> crearRuta(@RequestBody Ruta nuevaRuta) {
        Ruta creada = rutaService.crearRutaTentativa(nuevaRuta);
        return ResponseEntity.ok(creada);
    }

    /**
     * POST /rutas/{id}/asignar
     * Marca una ruta como asignada.
     */
    @PostMapping("/{id}/asignar")
    public ResponseEntity<Ruta> asignarRuta(@PathVariable("id") Long id) {
        Ruta asignada = rutaService.asignarRuta(id);
        return ResponseEntity.ok(asignada);
    }

    /**
     * GET /rutas
     * Lista todas las rutas.
     */
    @GetMapping
    public ResponseEntity<List<Ruta>> listarRutas() {
        List<Ruta> rutas = rutaService.listarRutas();
        return ResponseEntity.ok(rutas);
    }

    /**
     * POST /rutas/solicitud/{solicitudId}
     * Crea una ruta tentativa a partir de una solicitud existente
     * (consultando al micro de clientes-solicitudes).
     */
    @PostMapping("/solicitud/{solicitudId}")
    public ResponseEntity<Ruta> crearRutaParaSolicitud(
            @PathVariable("solicitudId") Long solicitudId) {

        Ruta ruta = rutaService.crearRutaParaSolicitud(solicitudId);
        return ResponseEntity.ok(ruta);
    }

    /**
     * GET /rutas/distancia
     * Calcula distancia y duración entre dos coordenadas usando OSRM.
     *
     * Ejemplo:
     *   GET /rutas/distancia?origenLat=-31.4135&origenLon=-64.18105&destinoLat=-32.9471&destinoLon=-60.6985
     */
    @GetMapping("/distancia")
    public ResponseEntity<DistanciaDTO> calcularDistancia(
            @RequestParam("origenLat") double origenLat,
            @RequestParam("origenLon") double origenLon,
            @RequestParam("destinoLat") double destinoLat,
            @RequestParam("destinoLon") double destinoLon) {

        DistanciaDTO distancia = rutaService.calcularDistanciaEntreCoordenadas(
                origenLat, origenLon, destinoLat, destinoLon);

        return ResponseEntity.ok(distancia);
    }
}
