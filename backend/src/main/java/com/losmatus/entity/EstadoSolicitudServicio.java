package com.losmatus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "estado_solicitud_servicio")
@Getter
@Setter
public class EstadoSolicitudServicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado", nullable = false)
    private Long idEstado;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Column(name = "nombre", length = 255)
    private String nombre;

}
