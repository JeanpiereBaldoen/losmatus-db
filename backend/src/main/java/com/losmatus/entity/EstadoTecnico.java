package com.losmatus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "estados_tecnico")
@Getter
@Setter
public class EstadoTecnico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_tecnico", nullable = false)
    private Long idEstadoTecnico;

    @Column(name = "nombre", nullable = false, length = 50, unique = true)
    private String nombre;

}
