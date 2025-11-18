package ar.edu.utn.frc.msrutastramos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tramo")
public class Tramo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ----- relación con RUTA -----
    @Column(name = "ruta_id", nullable = false)
    private Long rutaId;

    // ----- estado y tipo -----
    @Column(name = "estado", nullable = false, length = 30)
    private String estado;   // PENDIENTE / EN_PROCESO / FINALIZADO

    @Column(name = "tipo", nullable = false, length = 30)
    private String tipo;     // por ahora texto libre: NORMAL, URGENTE, etc.

    // ----- referencias a otros microservicios -----
    @Column(name = "camion_id")
    private Long camionId;

    @Column(name = "tarifa_id")
    private Long tarifaId;

    @Column(name = "origen_deposito_id")
    private Long origenDepositoId;

    @Column(name = "destino_deposito_id")
    private Long destinoDepositoId;

    // ----- tiempos estimados y reales -----
    @Column(name = "inicio_est")
    private LocalDateTime inicioEst;

    @Column(name = "fin_est")
    private LocalDateTime finEst;

    @Column(name = "inicio_real")
    private LocalDateTime inicioReal;

    @Column(name = "fin_real")
    private LocalDateTime finReal;

    // ----- costos y distancia -----
    @Column(name = "costo_aproximado", precision = 12, scale = 2)
    private BigDecimal costoAproximado;

    @Column(name = "costo_real", precision = 12, scale = 2)
    private BigDecimal costoReal;

    @Column(name = "distancia_km", precision = 10, scale = 3)
    private BigDecimal distanciaKm;

    @Column(name = "duracion_est_min")
    private Integer duracionEstMin;

    // ----- reglas de negocio -----
    public boolean puedeIniciarse() {
        return "PENDIENTE".equalsIgnoreCase(estado);
    }

    public boolean puedeFinalizarse() {
        return "EN_PROCESO".equalsIgnoreCase(estado);
    }
}
