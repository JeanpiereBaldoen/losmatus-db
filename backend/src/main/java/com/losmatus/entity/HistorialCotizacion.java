package com.losmatus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "historial_cotizacion")
@Getter
@Setter
public class HistorialCotizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historial_cotizacion", nullable = false)
    private Long idHistorialCotizacion;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "detalles_json", columnDefinition = "json")
    private String detallesJson;

    @Column(name = "fecha_generacion")
    private LocalDateTime fechaGeneracion;

    @Column(name = "monto_total_historico")
    private Double montoTotalHistorico;

    @Column(name = "ruta_archivo_pdf", length = 255)
    private String rutaArchivoPdf;

    @Column(name = "version")
    private Integer version;

    @Column(name = "id_solicitud", nullable = false)
    private Long idSolicitud;

    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @Column(name = "anio")
    private Integer anio;

    @Column(name = "codigo", length = 20)
    private String codigo;

    @Column(name = "correlativo")
    private Integer correlativo;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", insertable = false, updatable = false)
    private Usuario usuario;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_solicitud", referencedColumnName = "id_solicitud", insertable = false, updatable = false)
    private SolicitudServicio solicitud;

}
