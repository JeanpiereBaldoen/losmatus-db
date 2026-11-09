package com.losmatus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "severidades")
@Getter
@Setter
public class Severidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_severidad", nullable = false)
    private Long idSeveridad;

    @Column(name = "nivel", nullable = false, length = 50, unique = true)
    private String nivel;

}
