package com.losmatus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tecnicos")
@Getter
@Setter
public class Tecnico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tecnico", nullable = false)
    private Long idTecnico;

    @Column(name = "ape_materno_tecnico", length = 30)
    private String apeMaternoTecnico;

    @Column(name = "ape_paterno_tecnico", length = 30)
    private String apePaternoTecnico;

    @Column(name = "dni_tecnico", length = 20)
    private String dniTecnico;

    @Column(name = "nombre_tecnico", length = 30)
    private String nombreTecnico;

    @Column(name = "telefono", length = 255)
    private String telefono;

    @Column(name = "id_estado_tecnico")
    private Long idEstadoTecnico;

    @Column(name = "id_usuario", nullable = false, unique = true)
    private Long idUsuario;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", insertable = false, updatable = false)
    private Usuario usuario;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado_tecnico", referencedColumnName = "id_estado_tecnico", insertable = false, updatable = false)
    private EstadoTecnico estadoTecnico;

}
