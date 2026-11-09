package com.losmatus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;

@Entity
@Table(name = "imagenes_chat")
@Getter
@Setter
public class ImagenChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_imagen_chat", nullable = false)
    private Long idImagenChat;

    @Column(name = "fecha_subida")
    private LocalDateTime fechaSubida;

    @Column(name = "url_imagen", nullable = false, columnDefinition = "text")
    private String urlImagen;

    @Column(name = "id_chat", nullable = false)
    private Long idChat;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_chat", referencedColumnName = "id_chat", insertable = false, updatable = false)
    private ChatIncidencia chat;

}
