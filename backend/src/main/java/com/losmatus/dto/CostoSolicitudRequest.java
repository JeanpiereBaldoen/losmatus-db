package com.losmatus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CostoSolicitudRequest(
        @NotBlank(message = "El titulo del costo es obligatorio")
        String titulo,

        String descripcion,

        Integer cantidad,

        Double monto,

        Double montoTotal,

        Double costoProduccion,

        @NotNull(message = "El tipo de costo es obligatorio")
        Long idTipoCosto,

        Long idProducto,

        Long idProveedor
) {
}

