package com.losmatus.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiError(
        int status,
        String error,
        String message,
        LocalDateTime timestamp,
        Map<String, String> validaciones
) {
    public ApiError(int status, String error, String message) {
        this(status, error, message, LocalDateTime.now(), null);
    }

    public ApiError(int status, String error, String message, Map<String, String> validaciones) {
        this(status, error, message, LocalDateTime.now(), validaciones);
    }
}

