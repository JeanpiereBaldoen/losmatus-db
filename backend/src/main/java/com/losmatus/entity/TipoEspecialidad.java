package com.losmatus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tipo_especialidad")
@Getter
@Setter
public class TipoEspecialidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_especialidad", nullable = false)
    private Long idEspecialidad;

    @Column(name = "nombre_especialidad", nullable = false, length = 100, unique = true)
    private String nombreEspecialidad;

}
