package com.losmatus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tecnico_especialidad")
@Getter
@Setter
public class TecnicoEspecialidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tec_esp", nullable = false)
    private Long idTecEsp;

    @Column(name = "id_especialidad", nullable = false)
    private Long idEspecialidad;

    @Column(name = "id_tecnico", nullable = false)
    private Long idTecnico;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_especialidad", referencedColumnName = "id_especialidad", insertable = false, updatable = false)
    private TipoEspecialidad especialidad;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tecnico", referencedColumnName = "id_tecnico", insertable = false, updatable = false)
    private Tecnico tecnico;

}
