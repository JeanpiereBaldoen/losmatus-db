package com.losmatus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TecnicoRequest(
        @NotBlank(message = "El nombre del tecnico es obligatorio")
        @Size(max = 30)
        String nombreTecnico,

        @Size(max = 30)
        String apePaternoTecnico,

        @Size(max = 30)
        String apeMaternoTecnico,

        @Size(max = 20)
        String dniTecnico,

        @Size(max = 255)
        String telefono,

        Long idEstadoTecnico,

        @NotNull(message = "El ID de usuario es obligatorio")
        Long idUsuario
) {
}

