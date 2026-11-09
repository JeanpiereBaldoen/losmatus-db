package com.losmatus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "niveles_admin")
@Getter
@Setter
public class NivelAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nivel_admin", nullable = false)
    private Long idNivelAdmin;

    @Column(name = "nombre", nullable = false, length = 50, unique = true)
    private String nombre;

}
