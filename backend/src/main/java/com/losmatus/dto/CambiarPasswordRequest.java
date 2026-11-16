package com.losmatus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CambiarPasswordRequest(
        @NotBlank(message = "La contrasena actual es obligatoria")
        String passwordActual,

        @NotBlank(message = "La nueva contrasena es obligatoria")
        @Size(min = 6, max = 100, message = "La nueva contrasena debe tener al menos 6 caracteres")
        String passwordNuevo
) {
}

