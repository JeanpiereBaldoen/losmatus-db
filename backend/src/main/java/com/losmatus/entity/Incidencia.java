package com.losmatus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "incidencias")
@Getter
@Setter
public class Incidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_incidencia")
    private Long idIncidencia;

    @Column(length = 20, unique = true)
    private String codigo;

    @Column(name = "cumple_sla")
    private Boolean cumpleSla;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descripcion;

    @Column(name = "fecha_reporte")
    private LocalDateTime fechaReporte;

    @Column(name = "fecha_inicio_atencion")
    private LocalDateTime fechaInicioAtencion;

    @Column(name = "fecha_limite_resolucion")
    private LocalDateTime fechaLimiteResolucion;

    @Column(name = "fecha_finalizacion")
    private LocalDateTime fechaFinalizacion;

    @Column(name = "requiere_confirmacion_usuario")
    private Boolean requiereConfirmacionUsuario;

    @Column(name = "id_categoria")
    private Long idCategoria;

    @Column(name = "id_estado_incidencia")
    private Long idEstadoIncidencia;

    @Column(name = "id_severidad")
    private Long idSeveridad;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_reporta")
    private Usuario usuarioReporta;

}