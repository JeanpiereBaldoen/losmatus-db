package com.losmatus.dto;

import jakarta.validation.constraints.NotNull;

public record SolicitudServicioRequest(
        String descripcion,

        @NotNull(message = "El tipo de servicio es obligatorio")
        Long idTipoServicio,

        @NotNull(message = "La severidad es obligatoria")
        Long idSeveridad,

        Long idEstado,

        Boolean requiereCotizacion,

        Long idUsuario
) {
}

