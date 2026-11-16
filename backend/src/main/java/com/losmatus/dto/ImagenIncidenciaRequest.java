package com.losmatus.dto;

import jakarta.validation.constraints.NotBlank;

public record ImagenIncidenciaRequest(
        @NotBlank(message = "La URL de la imagen es obligatoria")
        String urlImagen
) {
}

