package com.losmatus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;

@Entity
@Table(name = "encuestas_satisfaccion")
@Getter
@Setter
public class EncuestaSatisfaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "comentario_general", columnDefinition = "text")
    private String comentarioGeneral;

    @Column(name = "fecha_encuesta")
    private LocalDateTime fechaEncuesta;

    @Column(name = "respuesta_1", nullable = false)
    private Integer respuesta1;

    @Column(name = "respuesta_2", nullable = false)
    private Integer respuesta2;

    @Column(name = "respuesta_3", nullable = false)
    private Integer respuesta3;

    @Column(name = "id_incidencia", nullable = false, unique = true)
    private Long idIncidencia;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_incidencia", referencedColumnName = "id_incidencia", insertable = false, updatable = false)
    private Incidencia incidencia;

}
