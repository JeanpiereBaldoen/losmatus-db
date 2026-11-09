package com.losmatus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "informes_mantenimiento")
@Getter
@Setter
public class InformeMantenimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "codigo", length = 255, unique = true)
    private String codigo;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "datos_dinamicos", columnDefinition = "json")
    private String datosDinamicos;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", columnDefinition = "enum('BORRADOR','FINALIZADO')")
    private Estado estado;

    @Column(name = "fecha_ejecucion")
    private LocalDateTime fechaEjecucion;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "fotos_url", columnDefinition = "json")
    private String fotosUrl;

    @Column(name = "id_equipo")
    private Long idEquipo;

    @Column(name = "id_plantilla")
    private Long idPlantilla;

    @Column(name = "id_tecnico")
    private Long idTecnico;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tecnico", referencedColumnName = "id_tecnico", insertable = false, updatable = false)
    private Tecnico tecnico;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_equipo", referencedColumnName = "id", insertable = false, updatable = false)
    private Equipo equipo;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_plantilla", referencedColumnName = "id", insertable = false, updatable = false)
    private PlantillaChecklist plantilla;

    public enum Estado {
        BORRADOR, FINALIZADO
    }

}
