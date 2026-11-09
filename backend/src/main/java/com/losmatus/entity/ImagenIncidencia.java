package com.losmatus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;

@Entity
@Table(name = "imagenes_incidencia")
@Getter
@Setter
public class ImagenIncidencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_imagen", nullable = false)
    private Long idImagen;

    @Column(name = "fecha_subida")
    private LocalDateTime fechaSubida;

    @Column(name = "url_imagen", nullable = false, columnDefinition = "text")
    private String urlImagen;

    @Column(name = "id_incidencia", nullable = false)
    private Long idIncidencia;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_incidencia", referencedColumnName = "id_incidencia", insertable = false, updatable = false)
    private Incidencia incidencia;

}
