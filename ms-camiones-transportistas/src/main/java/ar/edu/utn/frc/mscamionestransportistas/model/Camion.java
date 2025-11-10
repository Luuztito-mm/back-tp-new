package ar.edu.utn.frc.mscamionestransportistas.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "camion")
public class Camion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "dominio")
    private String dominio;

    @Column(name = "nombre_transportista")
    private String nombreTransportista;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "capacidad_peso_kg")
    private BigDecimal capacidadPesoKg;

    @Column(name = "capacidad_volumen_m3")
    private BigDecimal capacidadVolumenM3;

    @Column(name = "consumo_litros_km")
    private BigDecimal consumoLitrosKm;

    @Column(name = "costo_base_km")
    private BigDecimal costoBaseKm;

    @Column(name = "disponible")
    private Boolean disponible;

    public Camion() {}

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getDominio() {
        return dominio;
    }
    public void setDominio(String dominio) {
        this.dominio = dominio;
    }
    public String getNombreTransportista() {
        return nombreTransportista;
    }
    public void setNombreTransportista(String nombreTransportista) {
        this.nombreTransportista = nombreTransportista;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public BigDecimal getCapacidadPesoKg() {
        return capacidadPesoKg;
    }
    public void setCapacidadPesoKg(BigDecimal capacidadPesoKg) {
        this.capacidadPesoKg = capacidadPesoKg;
    }
    public BigDecimal getCapacidadVolumenM3() {
        return capacidadVolumenM3;
    }
    public void setCapacidadVolumenM3(BigDecimal capacidadVolumenM3) {
        this.capacidadVolumenM3 = capacidadVolumenM3;
    }
    public BigDecimal getConsumoLitrosKm() {
        return consumoLitrosKm;
    }
    public void setConsumoLitrosKm(BigDecimal consumoLitrosKm) {
        this.consumoLitrosKm = consumoLitrosKm;
    }
    public BigDecimal getCostoBaseKm() {
        return costoBaseKm;
    }
    public void setCostoBaseKm(BigDecimal costoBaseKm) {
        this.costoBaseKm = costoBaseKm;
    }
    public Boolean getDisponible() {
        return disponible;
    }
    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }
}
