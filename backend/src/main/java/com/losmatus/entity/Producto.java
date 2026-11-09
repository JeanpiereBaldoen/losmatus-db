package com.losmatus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "productos")
@Getter
@Setter
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "descripcion", columnDefinition = "text")
    private String descripcion;

    @Column(name = "nombre", nullable = false, length = 255)
    private String nombre;

    @Column(name = "precio_produccion")
    private Double precioProduccion;

    @Column(name = "precio_venta")
    private Double precioVenta;

}
