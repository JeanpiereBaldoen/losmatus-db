package com.losmatus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;

@Entity
@Table(name = "articulos_kb")
@Getter
@Setter
public class ArticuloKb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, columnDefinition = "enum('ARCHIVADO','BORRADOR','PUBLICADO')")
    private Estado estado;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_publicacion")
    private LocalDateTime fechaPublicacion;

    @Column(name = "palabras_clave", length = 500)
    private String palabrasClave;

    @Column(name = "solucion", nullable = false, columnDefinition = "text")
    private String solucion;

    @Column(name = "titulo", nullable = false, length = 255)
    private String titulo;

    @Column(name = "id_aprobador")
    private Long idAprobador;

    @Column(name = "id_autor")
    private Long idAutor;

    @Column(name = "id_categoria")
    private Long idCategoria;

    @Column(name = "id_incidencia_origen", unique = true)
    private Long idIncidenciaOrigen;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_autor", referencedColumnName = "id_usuario", insertable = false, updatable = false)
    private Usuario autor;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_incidencia_origen", referencedColumnName = "id_incidencia", insertable = false, updatable = false)
    private Incidencia incidenciaOrigen;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aprobador", referencedColumnName = "id_usuario", insertable = false, updatable = false)
    private Usuario aprobador;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria", insertable = false, updatable = false)
    private Categoria categoria;

    public enum Estado {
        ARCHIVADO, BORRADOR, PUBLICADO
    }

}
