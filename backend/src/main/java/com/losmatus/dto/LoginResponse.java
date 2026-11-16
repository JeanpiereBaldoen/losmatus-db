package com.losmatus.dto;

public record LoginResponse(
        String token,
        String tipo,
        Long idUsuario,
        String email,
        String rol
) {
    public LoginResponse(String token, Long idUsuario, String email, String rol) {
        this(token, "Bearer", idUsuario, email, rol);
    }
}

