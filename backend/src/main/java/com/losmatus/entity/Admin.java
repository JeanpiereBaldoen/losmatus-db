package com.losmatus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "admins")
@Getter
@Setter
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_admin", nullable = false)
    private Long idAdmin;

    @Column(name = "nombre_admin", length = 30)
    private String nombreAdmin;

    @Column(name = "telefono_admin", length = 15)
    private String telefonoAdmin;

    @Column(name = "id_nivel_admin")
    private Long idNivelAdmin;

    @Column(name = "id_usuario", nullable = false, unique = true)
    private Long idUsuario;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", insertable = false, updatable = false)
    private Usuario usuario;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nivel_admin", referencedColumnName = "id_nivel_admin", insertable = false, updatable = false)
    private NivelAdmin nivelAdmin;

}
