package com.losmatus.dto;

import jakarta.validation.constraints.NotNull;

public record CostoIncidenciaRequest(
        @NotNull(message = "El costo de produccion es obligatorio")
        Double costoProduccion,

        @NotNull(message = "El costo de venta es obligatorio")
        Double costoVenta,

        Double ganancia,

        Double igv,

        Double costoTotal,

        @NotNull(message = "El tipo de costo es obligatorio")
        Long idTipoCosto,

        Long idProveedor
) {
}

