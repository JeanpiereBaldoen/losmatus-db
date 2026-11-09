package com.losmatus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;

@Entity
@Table(name = "equipos")
@Getter
@Setter
public class Equipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "codigo_inventario", nullable = false, length = 255, unique = true)
    private String codigoInventario;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", columnDefinition = "enum('OPERATIVO','EN_MANTENIMIENTO','INOPERATIVO','DE_BAJA')")
    private Estado estado;

    @Column(name = "fecha_adquisicion")
    private LocalDate fechaAdquisicion;

    @Column(name = "fecha_ultimo_mantenimiento")
    private LocalDate fechaUltimoMantenimiento;

    @Column(name = "marca", length = 255)
    private String marca;

    @Column(name = "modelo", length = 255)
    private String modelo;

    @Column(name = "nombre", length = 255)
    private String nombre;

    @Column(name = "numero_serie", length = 255)
    private String numeroSerie;

    @Column(name = "ubicacion_fisica", length = 150)
    private String ubicacionFisica;

    @Column(name = "url_acceso", length = 500)
    private String urlAcceso;

    @Column(name = "id_empresa")
    private Long idEmpresa;

    @Column(name = "id_tipo_equipo", nullable = false)
    private Long idTipoEquipo;

    @Column(name = "id_usuario_asignado")
    private Long idUsuarioAsignado;

    @Column(name = "frecuencia_mantenimiento")
    private Integer frecuenciaMantenimiento;

    @Column(name = "id_area", nullable = false)
    private Long idArea;

    @Column(name = "id_sede", nullable = false)
    private Long idSede;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_area", referencedColumnName = "id", insertable = false, updatable = false)
    private Area area;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sede", referencedColumnName = "id", insertable = false, updatable = false)
    private Sede sede;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa", referencedColumnName = "id_empresa", insertable = false, updatable = false)
    private Empresa empresa;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_asignado", referencedColumnName = "id_usuario", insertable = false, updatable = false)
    private Usuario usuarioAsignado;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_equipo", referencedColumnName = "id", insertable = false, updatable = false)
    private TipoEquipo tipoEquipo;

    public enum Estado {
        OPERATIVO, EN_MANTENIMIENTO, INOPERATIVO, DE_BAJA
    }

}
