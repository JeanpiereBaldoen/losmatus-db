package com.losmatus.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.losmatus.dto.LoginResponse;
import com.losmatus.dto.RegistroUsuarioRequest;
import com.losmatus.dto.SolicitudServicioRequest;
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
class SolicitudServicioIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String registrarYObtenerToken(String prefijo) throws Exception {
        String randomSuffix = UUID.randomUUID().toString().substring(0, 8);
        String email = prefijo + "_" + randomSuffix + "@losmatus.test";
        String dni = String.valueOf(System.currentTimeMillis()).substring(4, 12);

        RegistroUsuarioRequest reg = new RegistroUsuarioRequest(
                email, "pass123456", dni, "Solicitante", "Test", null, "999111222", null
        );

        MvcResult res = mockMvc.perform(post("/api/auth/registro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reg)))
                .andExpect(status().isCreated())
                .andReturn();

        LoginResponse loginResponse = objectMapper.readValue(res.getResponse().getContentAsString(), LoginResponse.class);
        return loginResponse.token();
    }

    @Test
    void testAislamientoDeSolicitudesEntreClientes() throws Exception {
        String tokenCliente1 = registrarYObtenerToken("solic1");
        String tokenCliente2 = registrarYObtenerToken("solic2");

        SolicitudServicioRequest req = new SolicitudServicioRequest(
                "Mantenimiento de servidor de base de datos",
                1L, // tipo servicio 1
                2L, // severidad 2
                1L, // estado 1
                false,
                null
        );

        // 1. Cliente 1 crea su solicitud de servicio
        MvcResult resCrear = mockMvc.perform(post("/api/solicitudes-servicio")
                        .header("Authorization", "Bearer " + tokenCliente1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idSolicitud").isNumber())
                .andExpect(jsonPath("$.codigo").isNotEmpty())
                .andReturn();

        Long idSolicitud1 = objectMapper.readTree(resCrear.getResponse().getContentAsString()).get("idSolicitud").asLong();

        // 2. Cliente 1 puede acceder a su solicitud
        mockMvc.perform(get("/api/solicitudes-servicio/" + idSolicitud1)
                        .header("Authorization", "Bearer " + tokenCliente1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idSolicitud").value(idSolicitud1));

        // 3. Cliente 2 no puede acceder a la solicitud de Cliente 1 (403 Forbidden)
        mockMvc.perform(get("/api/solicitudes-servicio/" + idSolicitud1)
                        .header("Authorization", "Bearer " + tokenCliente2))
                .andExpect(status().isForbidden());
    }
}

