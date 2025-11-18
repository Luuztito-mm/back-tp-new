package ar.edu.utn.frc.msrutastramos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad Ruta, alineada con la tabla:
 *
 * CREATE TABLE ruta (
 *   id SERIAL PRIMARY KEY,
 *   solicitud_id       INT      NOT NULL,
 *   asignada           BOOLEAN  NOT NULL,
 *   cantidad_tramos    INT      NOT NULL,
 *   cantidad_depositos INT      NOT NULL
 * );
 */
@Entity
@Table(name = "ruta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ruta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Id de la solicitud asociada (viene de ms-clientes-solicitudes). */
    @Column(name = "solicitud_id", nullable = false)
    private Long solicitudId;

    /** Indica si la ruta ya fue confirmada/asignada. */
    @Builder.Default
    @Column(nullable = false)
    private Boolean asignada = false;

    /** Cantidad de tramos que componen esta ruta. */
    @Builder.Default
    @Column(name = "cantidad_tramos", nullable = false)
    private Integer cantidadTramos = 0;

    /** Cantidad de depósitos involucrados en la ruta. */
    @Builder.Default
    @Column(name = "cantidad_depositos", nullable = false)
    private Integer cantidadDepositos = 0;
}
