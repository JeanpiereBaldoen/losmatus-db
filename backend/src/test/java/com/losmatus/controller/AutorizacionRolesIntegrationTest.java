package com.losmatus.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.losmatus.dto.LoginResponse;
import com.losmatus.dto.RegistroUsuarioRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AutorizacionRolesIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String tokenCliente;

    @BeforeEach
    void setUp() throws Exception {
        String randomSuffix = UUID.randomUUID().toString().substring(0, 8);
        String email = "cliente_auth_" + randomSuffix + "@losmatus.test";
        String dni = String.valueOf(System.currentTimeMillis()).substring(4, 12);

        RegistroUsuarioRequest registro = new RegistroUsuarioRequest(
                email, "pass123456", dni, "Cliente", "Autorizacion", null, "999888777", null
        );

        MvcResult res = mockMvc.perform(post("/api/auth/registro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registro)))
                .andExpect(status().isCreated())
                .andReturn();

        LoginResponse login = objectMapper.readValue(res.getResponse().getContentAsString(), LoginResponse.class);
        this.tokenCliente = login.token();
    }

    @Test
    @DisplayName("Cliente no puede crear tecnicos (403 Forbidden)")
    void clienteNoPuedeCrearTecnicos() throws Exception {
        String body = "{\"nombreTecnico\":\"Juan\",\"idUsuario\":1}";
        mockMvc.perform(post("/api/tecnicos")
                        .header("Authorization", "Bearer " + tokenCliente)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Cliente no puede crear proveedores (403 Forbidden)")
    void clienteNoPuedeCrearProveedores() throws Exception {
        String body = "{\"nombreProveedor\":\"Tech Corp\",\"rucProveedor\":\"20123456789\",\"estado\":1}";
        mockMvc.perform(post("/api/proveedores")
                        .header("Authorization", "Bearer " + tokenCliente)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Cliente no puede listar proveedores (403 Forbidden)")
    void clienteNoPuedeListarProveedores() throws Exception {
        mockMvc.perform(get("/api/proveedores")
                        .header("Authorization", "Bearer " + tokenCliente))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Cliente no puede crear categorias (403 Forbidden)")
    void clienteNoPuedeCrearCategorias() throws Exception {
        String body = "{\"nombreCategoria\":\"Nueva Categoria\"}";
        mockMvc.perform(post("/api/categorias")
                        .header("Authorization", "Bearer " + tokenCliente)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Cliente no puede crear estados de incidencia (403 Forbidden)")
    void clienteNoPuedeCrearEstadosIncidencia() throws Exception {
        String body = "{\"nombre\":\"Nuevo Estado\"}";
        mockMvc.perform(post("/api/estados-incidencia")
                        .header("Authorization", "Bearer " + tokenCliente)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Cliente no puede crear equipos (403 Forbidden)")
    void clienteNoPuedeCrearEquipos() throws Exception {
        String body = "{\"codigoInventario\":\"EQ-999\",\"nombre\":\"Servidor\",\"idTipoEquipo\":1,\"idArea\":1,\"idSede\":1}";
        mockMvc.perform(post("/api/equipos")
                        .header("Authorization", "Bearer " + tokenCliente)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Cliente no puede eliminar equipos (403 Forbidden)")
    void clienteNoPuedeEliminarEquipos() throws Exception {
        mockMvc.perform(delete("/api/equipos/1")
                        .header("Authorization", "Bearer " + tokenCliente))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Cliente no puede acceder a pistas de auditoria (403 Forbidden)")
    void clienteNoPuedeAccederAuditoria() throws Exception {
        mockMvc.perform(get("/api/auditoria-informes")
                        .header("Authorization", "Bearer " + tokenCliente))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Cliente no puede crear administradores (403 Forbidden)")
    void clienteNoPuedeCrearAdmins() throws Exception {
        String body = "{\"nombreAdmin\":\"Super Admin\",\"idUsuario\":1}";
        mockMvc.perform(post("/api/admins")
                        .header("Authorization", "Bearer " + tokenCliente)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Cliente no puede crear usuarios (403 Forbidden)")
    void clienteNoPuedeCrearUsuarios() throws Exception {
        String body = "{\"email\":\"otro@losmatus.test\",\"password\":\"password123\",\"idRol\":1}";
        mockMvc.perform(post("/api/usuarios")
                        .header("Authorization", "Bearer " + tokenCliente)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Cliente no puede consultar costos de incidencias (403 Forbidden)")
    void clienteNoPuedeConsultarCostosIncidencia() throws Exception {
        mockMvc.perform(get("/api/incidencias/1/costos")
                        .header("Authorization", "Bearer " + tokenCliente))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Cliente no puede consultar costos de solicitudes (403 Forbidden)")
    void clienteNoPuedeConsultarCostosSolicitud() throws Exception {
        mockMvc.perform(get("/api/solicitudes-servicio/1/costos")
                        .header("Authorization", "Bearer " + tokenCliente))
                .andExpect(status().isForbidden());
    }
}

