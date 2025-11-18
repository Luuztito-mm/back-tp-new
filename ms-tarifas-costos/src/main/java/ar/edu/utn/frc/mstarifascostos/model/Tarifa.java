package ar.edu.utn.frc.mstarifascostos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Entidad Tarifa, alineada con la tabla:
 *
 * CREATE TABLE tarifa (
 *   id SERIAL PRIMARY KEY,
 *   nombre                   VARCHAR(100) NOT NULL,
 *   rango_peso_min           DECIMAL(10,2),
 *   rango_peso_max           DECIMAL(10,2),
 *   rango_volumen_min        DECIMAL(10,3),
 *   rango_volumen_max        DECIMAL(10,3),
 *   costo_km_base            DECIMAL(10,2) NOT NULL,
 *   cargo_gestion_por_tramo  DECIMAL(10,2) NOT NULL
 * );
 */
@Entity
@Table(name = "tarifa")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(name = "rango_peso_min", precision = 10, scale = 2)
    private BigDecimal rangoPesoMin;

    @Column(name = "rango_peso_max", precision = 10, scale = 2)
    private BigDecimal rangoPesoMax;

    @Column(name = "rango_volumen_min", precision = 10, scale = 3)
    private BigDecimal rangoVolumenMin;

    @Column(name = "rango_volumen_max", precision = 10, scale = 3)
    private BigDecimal rangoVolumenMax;

    @Column(name = "costo_km_base", nullable = false, precision = 10, scale = 2)
    private BigDecimal costoKmBase;

    @Column(name = "cargo_gestion_por_tramo", nullable = false, precision = 10, scale = 2)
    private BigDecimal cargoGestionPorTramo;
}
