package com.losmatus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "modos_atencion")
@Getter
@Setter
public class ModoAtencion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_modo_atencion", nullable = false)
    private Long idModoAtencion;

    @Column(name = "nombre", nullable = false, length = 50, unique = true)
    private String nombre;

}
