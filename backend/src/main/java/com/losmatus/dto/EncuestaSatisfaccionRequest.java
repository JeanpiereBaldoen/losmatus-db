package com.losmatus.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record EncuestaSatisfaccionRequest(
        @NotNull(message = "La respuesta 1 es obligatoria")
        @Min(value = 1, message = "La calificacion minima es 1")
        @Max(value = 5, message = "La calificacion maxima es 5")
        Integer respuesta1,

        @NotNull(message = "La respuesta 2 es obligatoria")
        @Min(value = 1, message = "La calificacion minima es 1")
        @Max(value = 5, message = "La calificacion maxima es 5")
        Integer respuesta2,

        @NotNull(message = "La respuesta 3 es obligatoria")
        @Min(value = 1, message = "La calificacion minima es 1")
        @Max(value = 5, message = "La calificacion maxima es 5")
        Integer respuesta3,

        String comentarioGeneral
) {
}

