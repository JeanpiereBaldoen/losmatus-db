package com.losmatus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;

@Entity
@Table(name = "costos_solicitud")
@Getter
@Setter
public class CostoSolicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_costo", nullable = false)
    private Long idCosto;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "descripcion", columnDefinition = "text")
    private String descripcion;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "monto")
    private Double monto;

    @Column(name = "monto_total")
    private Double montoTotal;

    @Column(name = "titulo", nullable = false, length = 150)
    private String titulo;

    @Column(name = "id_producto")
    private Long idProducto;

    @Column(name = "id_proveedor")
    private Long idProveedor;

    @Column(name = "id_solicitud", nullable = false)
    private Long idSolicitud;

    @Column(name = "id_tipo_costo", nullable = false)
    private Long idTipoCosto;

    @Column(name = "costo_produccion")
    private Double costoProduccion;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_solicitud", referencedColumnName = "id_solicitud", insertable = false, updatable = false)
    private SolicitudServicio solicitud;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proveedor", referencedColumnName = "id_proveedor", insertable = false, updatable = false)
    private Proveedor proveedor;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_costo", referencedColumnName = "id_tipo_costo", insertable = false, updatable = false)
    private TipoCosto tipoCosto;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", referencedColumnName = "id", insertable = false, updatable = false)
    private Producto producto;

}
