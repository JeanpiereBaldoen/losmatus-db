package com.losmatus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;

@Entity
@Table(name = "historial_mantenimiento_equipos")
@Getter
@Setter
public class HistorialMantenimientoEquipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "descripcion", columnDefinition = "text")
    private String descripcion;

    @Column(name = "fecha_evento")
    private LocalDateTime fechaEvento;

    @Column(name = "id_informe")
    private Long idInforme;

    @Column(name = "tecnico_responsable", length = 255)
    private String tecnicoResponsable;

    @Column(name = "tipo_evento", length = 50)
    private String tipoEvento;

    @Column(name = "id_equipo", nullable = false)
    private Long idEquipo;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_equipo", referencedColumnName = "id", insertable = false, updatable = false)
    private Equipo equipo;

}
