package com.losmatus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record IncidenciaRequest(

        @NotBlank @Size(max = 20) String codigo,

        @NotBlank String descripcion,

        @NotNull Long idCategoria,

        @NotNull Long idEstadoIncidencia,

        @NotNull Long idSeveridad,

        @NotNull Long idUsuarioReporta
        

) {
}