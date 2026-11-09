package com.losmatus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tipos_equipo")
@Getter
@Setter
public class TipoEquipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Column(name = "frecuencia_mantenimiento_sugerida")
    private Integer frecuenciaMantenimientoSugerida;

    @Column(name = "nombre", nullable = false, length = 255, unique = true)
    private String nombre;

}
