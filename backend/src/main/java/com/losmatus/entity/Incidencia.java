package com.losmatus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;

@Entity
@Table(name = "incidencias")
@Getter
@Setter
public class Incidencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_incidencia", nullable = false)
    private Long idIncidencia;

    @Column(name = "codigo", length = 20, unique = true)
    private String codigo;

    @Column(name = "cumple_sla")
    private Boolean cumpleSla;

    @Column(name = "descripcion", nullable = false, columnDefinition = "text")
    private String descripcion;

    @Column(name = "fecha_finalizacion")
    private LocalDateTime fechaFinalizacion;

    @Column(name = "fecha_inicio_atencion")
    private LocalDateTime fechaInicioAtencion;

    @Column(name = "fecha_limite_resolucion")
    private LocalDateTime fechaLimiteResolucion;

    @Column(name = "fecha_reporte")
    private LocalDateTime fechaReporte;

    @Column(name = "requiere_confirmacion_usuario")
    private Boolean requiereConfirmacionUsuario;

    @Column(name = "id_categoria")
    private Long idCategoria;

    @Column(name = "id_estado_incidencia")
    private Long idEstadoIncidencia;

    @Column(name = "id_severidad")
    private Long idSeveridad;

    @Column(name = "id_usuario_reporta")
    private Long idUsuarioReporta;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_reporta", referencedColumnName = "id_usuario", insertable = false, updatable = false)
    private Usuario usuarioReporta;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria", insertable = false, updatable = false)
    private Categoria categoria;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado_incidencia", referencedColumnName = "id_estado_incidencia", insertable = false, updatable = false)
    private EstadoIncidencia estadoIncidencia;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_severidad", referencedColumnName = "id_severidad", insertable = false, updatable = false)
    private Severidad severidad;

}
