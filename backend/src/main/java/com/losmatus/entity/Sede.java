package com.losmatus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "sedes")
@Getter
@Setter
public class Sede {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @Column(name = "nombre_encargado_sede", length = 150)
    private String nombreEncargadoSede;

    @Column(name = "nombre_sede", nullable = false, length = 150)
    private String nombreSede;

    @Column(name = "ubicacion_sede", length = 250)
    private String ubicacionSede;

    @Column(name = "ubicacion_sede_url", length = 500)
    private String ubicacionSedeUrl;

    @Column(name = "id_empresa_legal", nullable = false)
    private Long idEmpresaLegal;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa_legal", referencedColumnName = "id_detalle_empresa", insertable = false, updatable = false)
    private EmpresaDetalle empresaLegal;

}
