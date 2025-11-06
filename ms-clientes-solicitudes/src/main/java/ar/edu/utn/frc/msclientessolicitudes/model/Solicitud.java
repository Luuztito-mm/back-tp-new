package ar.edu.utn.frc.msclientessolicitudes.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitud")
@Getter
@Setter
@NoArgsConstructor
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @Column(name = "contenedor_id", nullable = false)
    private Long contenedorId;

    @Column(name = "estado", length = 30)
    private String estado;

    @Column(name = "costo_estimado", precision = 12, scale = 2)
    private BigDecimal costoEstimado;

    @Column(name = "tiempo_estimado_min")
    private Integer tiempoEstimadoMin;

    // las columnas de origen/destino las dejamos sin mapear por ahora
    // si querés las agregamos después
}

