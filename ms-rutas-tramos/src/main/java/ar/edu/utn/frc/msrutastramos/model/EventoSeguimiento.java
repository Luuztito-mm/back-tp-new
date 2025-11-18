package ar.edu.utn.frc.msrutastramos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Representa la tabla evento_seguimiento del DER:
 *
 *  - id              (PK)
 *  - solicitud_id    (FK a solicitud)
 *  - fecha_hora
 *  - estado          (texto: "EN_DEPOSITO", "EN_CAMINO", etc.)
 *  - ubicacion_texto (descripción libre)
 *  - deposito_id     (opcional, FK a depósito)
 */
@Entity
@Table(name = "evento_seguimiento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventoSeguimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "solicitud_id", nullable = false)
    private Long solicitudId;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "estado", nullable = false, length = 50)
    private String estado;

    @Column(name = "ubicacion_texto")
    private String ubicacionTexto;

    @Column(name = "deposito_id")
    private Long depositoId;
}
