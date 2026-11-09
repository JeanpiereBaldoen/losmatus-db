package com.losmatus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "estados_incidencia")
@Getter
@Setter
public class EstadoIncidencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_incidencia", nullable = false)
    private Long idEstadoIncidencia;

    @Column(name = "nombre", nullable = false, length = 50, unique = true)
    private String nombre;

}
