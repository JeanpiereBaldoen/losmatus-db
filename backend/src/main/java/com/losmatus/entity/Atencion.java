package com.losmatus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;

@Entity
@Table(name = "atenciones")
@Getter
@Setter
public class Atencion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_atencion", nullable = false)
    private Long idAtencion;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "observaciones", columnDefinition = "text")
    private String observaciones;

    @Column(name = "id_incidencia", nullable = false)
    private Long idIncidencia;

    @Column(name = "id_modo_atencion")
    private Long idModoAtencion;

    @Column(name = "id_tecnico")
    private Long idTecnico;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_modo_atencion", referencedColumnName = "id_modo_atencion", insertable = false, updatable = false)
    private ModoAtencion modoAtencion;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tecnico", referencedColumnName = "id_tecnico", insertable = false, updatable = false)
    private Tecnico tecnico;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_incidencia", referencedColumnName = "id_incidencia", insertable = false, updatable = false)
    private Incidencia incidencia;

}
