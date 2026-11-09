package com.losmatus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "configuracion_sla", uniqueConstraints = {@UniqueConstraint(columnNames = {"id_usuario", "id_severidad"})})
@Getter
@Setter
public class ConfiguracionSla {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "tiempo_resolucion_horas", nullable = false)
    private Integer tiempoResolucionHoras;

    @Column(name = "tiempo_respuesta_horas")
    private Integer tiempoRespuestaHoras;

    @Column(name = "id_severidad", nullable = false)
    private Long idSeveridad;

    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_severidad", referencedColumnName = "id_severidad", insertable = false, updatable = false)
    private Severidad severidad;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", insertable = false, updatable = false)
    private Usuario usuario;

}
