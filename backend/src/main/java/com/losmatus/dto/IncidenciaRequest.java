package com.losmatus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record IncidenciaRequest(

        @Size(max = 20) String codigo,

        @NotBlank String descripcion,

        @NotNull Long idCategoria,

        Long idEstadoIncidencia,

        @NotNull Long idSeveridad,

        Long idUsuarioReporta

) {
}
