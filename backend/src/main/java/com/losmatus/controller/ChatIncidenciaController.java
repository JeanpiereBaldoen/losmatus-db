package com.losmatus.controller;

import com.losmatus.config.SecurityUtils;
import com.losmatus.dto.ChatIncidenciaDTO;
import com.losmatus.dto.ChatIncidenciaRequest;
import com.losmatus.entity.ChatIncidencia;
import com.losmatus.entity.Usuario;
import com.losmatus.repository.ChatIncidenciaRepository;
import com.losmatus.repository.UsuarioRepository;
import com.losmatus.service.IncidenciaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/incidencias/{idIncidencia}/chats")
public class ChatIncidenciaController {

    private final ChatIncidenciaRepository chatRepo;
    private final IncidenciaService incidenciaService;
    private final UsuarioRepository usuarioRepository;

    public ChatIncidenciaController(
            ChatIncidenciaRepository chatRepo,
            IncidenciaService incidenciaService,
            UsuarioRepository usuarioRepository
    ) {
        this.chatRepo = chatRepo;
        this.incidenciaService = incidenciaService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<ChatIncidenciaDTO>> listar(@PathVariable Long idIncidencia) {
        incidenciaService.buscarPorId(idIncidencia); // Valida existencia y permisos del cliente
        List<ChatIncidenciaDTO> lista = chatRepo.findByIdIncidenciaOrderByFechaEnvioAsc(idIncidencia).stream()
                .map(c -> {
                    String email = usuarioRepository.findById(c.getIdUsuario())
                            .map(Usuario::getEmail)
                            .orElse(null);
                    return new ChatIncidenciaDTO(
                            c.getIdChat(),
                            c.getMensaje(),
                            c.getFechaEnvio(),
                            c.getIdUsuario(),
                            email,
                            c.getIdIncidencia()
                    );
                })
                .toList();
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ChatIncidenciaDTO> enviarMensaje(
            @PathVariable Long idIncidencia,
            @Valid @RequestBody ChatIncidenciaRequest request
    ) {
        incidenciaService.buscarPorId(idIncidencia); // Valida permisos

        Long idUsuarioActual = SecurityUtils.getCurrentUserId();
        ChatIncidencia chat = new ChatIncidencia();
        chat.setIdIncidencia(idIncidencia);
        chat.setIdUsuario(idUsuarioActual);
        chat.setMensaje(request.mensaje().trim());
        chat.setFechaEnvio(LocalDateTime.now());

        ChatIncidencia guardado = chatRepo.save(chat);
        String email = SecurityUtils.getCurrentUserEmail();

        return ResponseEntity.status(HttpStatus.CREATED).body(new ChatIncidenciaDTO(
                guardado.getIdChat(),
                guardado.getMensaje(),
                guardado.getFechaEnvio(),
                guardado.getIdUsuario(),
                email,
                guardado.getIdIncidencia()
        ));
    }
}

