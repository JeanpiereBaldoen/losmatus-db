package com.losmatus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "empresa_detalle")
@Getter
@Setter
public class EmpresaDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_empresa", nullable = false)
    private Long idDetalleEmpresa;

    @Column(name = "razon_social", nullable = false, length = 200)
    private String razonSocial;

    @Column(name = "representante_legal", length = 150)
    private String representanteLegal;

    @Column(name = "ruc_empresa", nullable = false, length = 11, unique = true)
    private String rucEmpresa;

}
