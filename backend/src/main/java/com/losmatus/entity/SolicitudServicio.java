package com.losmatus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitudes_servicio")
@Getter
@Setter
public class SolicitudServicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solicitud", nullable = false)
    private Long idSolicitud;

    @Column(name = "anio")
    private Integer anio;

    @Column(name = "codigo", length = 20, unique = true)
    private String codigo;

    @Column(name = "correlativo")
    private Integer correlativo;

    @Column(name = "descripcion", columnDefinition = "text")
    private String descripcion;

    @Column(name = "fecha_solicitud")
    private LocalDateTime fechaSolicitud;

    @Column(name = "id_estado", nullable = false)
    private Long idEstado;

    @Column(name = "id_severidad", nullable = false)
    private Long idSeveridad;

    @Column(name = "id_tipo_servicio", nullable = false)
    private Long idTipoServicio;

    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @Column(name = "requiere_cotizacion", nullable = false)
    private Boolean requiereCotizacion;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", insertable = false, updatable = false)
    private Usuario usuario;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_severidad", referencedColumnName = "id_severidad", insertable = false, updatable = false)
    private Severidad severidad;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_servicio", referencedColumnName = "id_tipo_servicio", insertable = false, updatable = false)
    private TipoServicio tipoServicio;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado", referencedColumnName = "id_estado", insertable = false, updatable = false)
    private EstadoSolicitudServicio estado;

}
