package com.losmatus.config;

import com.losmatus.exception.UnauthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static CustomUserDetails getCurrentUserDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof CustomUserDetails userDetails)) {
            throw new UnauthorizedException("Usuario no autenticado");
        }
        return userDetails;
    }

    public static Long getCurrentUserId() {
        return getCurrentUserDetails().getIdUsuario();
    }

    public static String getCurrentUserEmail() {
        return getCurrentUserDetails().getUsername();
    }

    public static String getCurrentUserRole() {
        return getCurrentUserDetails().getRolNombre();
    }

    public static boolean isCliente() {
        return "CLIENTE".equalsIgnoreCase(getCurrentUserRole());
    }

    public static boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(getCurrentUserRole());
    }

    public static boolean isTecnico() {
        return "TECNICO".equalsIgnoreCase(getCurrentUserRole());
    }
}

