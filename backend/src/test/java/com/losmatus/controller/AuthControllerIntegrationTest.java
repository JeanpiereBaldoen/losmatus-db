package com.losmatus.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.losmatus.dto.LoginRequest;
import com.losmatus.dto.RegistroUsuarioRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testRegistroYLoginClienteExitoso() throws Exception {
        String randomSuffix = UUID.randomUUID().toString().substring(0, 8);
        String uniqueEmail = "cliente_" + randomSuffix + "@test.com";
        String uniqueDni = String.valueOf(System.currentTimeMillis()).substring(3, 11);

        RegistroUsuarioRequest registroRequest = new RegistroUsuarioRequest(
                uniqueEmail,
                "passwordSeguro123",
                uniqueDni,
                "Juan",
                "Perez",
                "Lopez",
                "999888777",
                "Calle 123"
        );

        // 1. Registro publico de CLIENTE
        mockMvc.perform(post("/api/auth/registro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registroRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.email").value(uniqueEmail))
                .andExpect(jsonPath("$.rol").value("CLIENTE"));

        // 2. Login con las nuevas credenciales
        LoginRequest loginRequest = new LoginRequest(uniqueEmail, "passwordSeguro123");
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.email").value(uniqueEmail))
                .andExpect(jsonPath("$.rol").value("CLIENTE"));
    }

    @Test
    void testLoginConCredencialesInvalidas() throws Exception {
        LoginRequest loginRequest = new LoginRequest("noexiste@test.com", "passwordErroneo");
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Unauthorized"));
    }

    @Test
    void testRegistroValidacionCamposRequeridos() throws Exception {
        RegistroUsuarioRequest invalido = new RegistroUsuarioRequest(
                "email-invalido",
                "123", // demasiado corto
                "",
                "",
                null, null, null, null
        );

        mockMvc.perform(post("/api/auth/registro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalido)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validaciones").isMap());
    }
}

