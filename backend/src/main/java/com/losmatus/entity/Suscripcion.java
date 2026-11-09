package com.losmatus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;

@Entity
@Table(name = "suscripciones", uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id", "modulo_id"})})
@Getter
@Setter
public class Suscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;

    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio;

    @Column(name = "modulo_id", nullable = false)
    private Long moduloId;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modulo_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Modulo modulo;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id_usuario", insertable = false, updatable = false)
    private Usuario usuario;

}
