package com.losmatus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;

@Entity
@Table(name = "costos_incidencia")
@Getter
@Setter
public class CostoIncidencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_costo", nullable = false)
    private Long idCosto;

    @Column(name = "costo_produccion", nullable = false)
    private Double costoProduccion;

    @Column(name = "costo_total", nullable = false)
    private Double costoTotal;

    @Column(name = "costo_venta", nullable = false)
    private Double costoVenta;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    @Column(name = "ganancia", nullable = false)
    private Double ganancia;

    @Column(name = "igv", nullable = false)
    private Double igv;

    @Column(name = "id_incidencia", nullable = false)
    private Long idIncidencia;

    @Column(name = "id_proveedor")
    private Long idProveedor;

    @Column(name = "id_tipo_costo", nullable = false)
    private Long idTipoCosto;

    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_costo", referencedColumnName = "id_tipo_costo", insertable = false, updatable = false)
    private TipoCosto tipoCosto;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_incidencia", referencedColumnName = "id_incidencia", insertable = false, updatable = false)
    private Incidencia incidencia;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proveedor", referencedColumnName = "id_proveedor", insertable = false, updatable = false)
    private Proveedor proveedor;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", insertable = false, updatable = false)
    private Usuario usuario;

}
