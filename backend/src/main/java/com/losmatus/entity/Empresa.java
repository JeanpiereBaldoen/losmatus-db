package com.losmatus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "empresas")
@Getter
@Setter
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empresa", nullable = false)
    private Long idEmpresa;

    @Column(name = "direccion", length = 255)
    private String direccion;

    @Column(name = "nombre_sede", length = 150)
    private String nombreSede;

    @Column(name = "representante_sede", length = 150)
    private String representanteSede;

    @Column(name = "telefono_empresa", length = 20)
    private String telefonoEmpresa;

    @Column(name = "url_direccion", length = 255)
    private String urlDireccion;

    @Column(name = "id_detalle_empresa", nullable = false)
    private Long idDetalleEmpresa;

    @Column(name = "usuario_id", nullable = false, unique = true)
    private Long usuarioId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id_usuario", insertable = false, updatable = false)
    private Usuario usuario;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_detalle_empresa", referencedColumnName = "id_detalle_empresa", insertable = false, updatable = false)
    private EmpresaDetalle detalleEmpresa;

}
