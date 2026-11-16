package com.losmatus.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.losmatus.dto.ChatIncidenciaRequest;
import com.losmatus.dto.IncidenciaRequest;
import com.losmatus.dto.LoginResponse;
import com.losmatus.dto.RegistroUsuarioRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class IncidenciaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private LoginResponse registrarYObtenerLogin(String prefijo) throws Exception {
        String randomSuffix = UUID.randomUUID().toString().substring(0, 8);
        String email = prefijo + "_" + randomSuffix + "@losmatus.test";
        String dni = String.valueOf(System.currentTimeMillis()).substring(4, 12);

        RegistroUsuarioRequest reg = new RegistroUsuarioRequest(
                email, "pass123456", dni, "Test", "User", null, "999000111", null
        );

        MvcResult res = mockMvc.perform(post("/api/auth/registro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reg)))
                .andExpect(status().isCreated())
                .andReturn();

        return objectMapper.readValue(res.getResponse().getContentAsString(), LoginResponse.class);
    }

    @Test
    void testAislamientoDeIncidenciasYChatEntreClientes() throws Exception {
        // 1. Crear dos clientes distintos
        LoginResponse loginA = registrarYObtenerLogin("clientea");
        LoginResponse loginB = registrarYObtenerLogin("clienteb");

        String codigoIncidencia = "INC-" + UUID.randomUUID().toString().substring(0, 8);
        IncidenciaRequest reqA = new IncidenciaRequest(
                codigoIncidencia,
                "Problema de conexion con switch principal",
                1L, // Categoria Hardware
                1L, // Estado Abierta
                2L, // Severidad Media
                loginA.idUsuario() // ID del cliente A
        );

        // 2. Cliente A crea su incidencia
        MvcResult resCrear = mockMvc.perform(post("/api/incidencias")
                        .header("Authorization", "Bearer " + loginA.token())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reqA)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idIncidencia").isNumber())
                .andExpect(jsonPath("$.codigo").value(codigoIncidencia))
                .andReturn();

        Long idIncidenciaA = objectMapper.readTree(resCrear.getResponse().getContentAsString()).get("idIncidencia").asLong();

        // 3. Cliente A puede consultar su incidencia por ID
        mockMvc.perform(get("/api/incidencias/" + idIncidenciaA)
                        .header("Authorization", "Bearer " + loginA.token()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo").value(codigoIncidencia));

        // 4. Cliente B intenta consultar la incidencia del Cliente A -> 403 Forbidden
        mockMvc.perform(get("/api/incidencias/" + idIncidenciaA)
                        .header("Authorization", "Bearer " + loginB.token()))
                .andExpect(status().isForbidden());

        // 5. Cliente A envia mensaje en el chat de su incidencia
        ChatIncidenciaRequest chatReq = new ChatIncidenciaRequest("Hola, adjunto detalles del fallo");
        mockMvc.perform(post("/api/incidencias/" + idIncidenciaA + "/chats")
                        .header("Authorization", "Bearer " + loginA.token())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(chatReq)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.mensaje").value("Hola, adjunto detalles del fallo"));

        // 6. Cliente B intenta acceder al chat de la incidencia de Cliente A -> 403 Forbidden
        mockMvc.perform(get("/api/incidencias/" + idIncidenciaA + "/chats")
                        .header("Authorization", "Bearer " + loginB.token()))
                .andExpect(status().isForbidden());
    }
}
