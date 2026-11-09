package com.losmatus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;

@Entity
@Table(name = "imagenes_solicitud")
@Getter
@Setter
public class ImagenSolicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "fecha_subida")
    private LocalDateTime fechaSubida;

    @Column(name = "url_imagen", nullable = false, length = 255)
    private String urlImagen;

    @Column(name = "solicitud_id", nullable = false)
    private Long solicitudId;

    @Column(name = "tipo_documento", length = 50)
    private String tipoDocumento;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitud_id", referencedColumnName = "id_solicitud", insertable = false, updatable = false)
    private SolicitudServicio solicitud;

}
