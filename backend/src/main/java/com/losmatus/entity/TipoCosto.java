package com.losmatus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tipo_costo")
@Getter
@Setter
public class TipoCosto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_costo", nullable = false)
    private Long idTipoCosto;

    @Column(name = "nombre_tipo_costo", nullable = false, length = 100, unique = true)
    private String nombreTipoCosto;

}
