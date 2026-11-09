package com.losmatus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;

@Entity
@Table(name = "personas")
@Getter
@Setter
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona", nullable = false)
    private Long idPersona;

    @Column(name = "ape_materno_persona", length = 30)
    private String apeMaternoPersona;

    @Column(name = "ape_paterno_persona", length = 30)
    private String apePaternoPersona;

    @Column(name = "direccion", length = 255)
    private String direccion;

    @Column(name = "dni", nullable = false, length = 255, unique = true)
    private String dni;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "nombre_persona", nullable = false, length = 30)
    private String nombrePersona;

    @Column(name = "telefono", length = 255)
    private String telefono;

    @Column(name = "url_direccion", length = 255)
    private String urlDireccion;

    @Column(name = "usuario_id", nullable = false, unique = true)
    private Long usuarioId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id_usuario", insertable = false, updatable = false)
    private Usuario usuario;

}
