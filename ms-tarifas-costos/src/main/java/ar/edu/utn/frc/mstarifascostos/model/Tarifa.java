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

    @Column(nullable = false)
    private String nombre;

    @Column(name = "rango_peso_min")
    private Double rangoPesoMin;

    @Column(name = "rango_peso_max")
    private Double rangoPesoMax;

    @Column(name = "rango_volumen_min")
    private Double rangoVolumenMin;

    @Column(name = "rango_volumen_max")
    private Double rangoVolumenMax;

    @Column(name = "costo_km_base")
    private Double costoKmBase;

    @Column(name = "cargo_gestion_por_tramo")
    private Double cargoGestionPorTramo;

    @Column(name = "costo_fijo")
    private Double costoFijo;

    @Column(name = "costo_km")
    private Double costoKm;

    @Column(name = "tipo_servicio")
    private String tipoServicio;
}
