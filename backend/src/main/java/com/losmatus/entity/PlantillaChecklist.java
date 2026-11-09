package com.losmatus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "plantillas_checklist")
@Getter
@Setter
public class PlantillaChecklist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "estructura", columnDefinition = "json")
    private String estructura;

    @Column(name = "nombre", length = 255)
    private String nombre;

    @Column(name = "tipo_equipo", length = 255)
    private String tipoEquipo;

}
