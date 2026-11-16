package com.losmatus.dto;

import jakarta.validation.constraints.NotBlank;

public record ChatIncidenciaRequest(
        @NotBlank(message = "El mensaje no puede estar vacio")
        String mensaje
) {
}

