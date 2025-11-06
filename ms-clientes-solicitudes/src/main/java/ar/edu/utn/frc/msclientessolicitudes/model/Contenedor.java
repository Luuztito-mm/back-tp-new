package ar.edu.utn.frc.msclientessolicitudes.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "contenedor")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Contenedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // en tu modelo: peso_kg, volumen_m3, estado, cliente_id
    @Column(name = "peso_kg", nullable = false)
    private Double pesoKg;

    @Column(name = "volumen_m3", nullable = false)
    private Double volumenM3;

    private String estado;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
