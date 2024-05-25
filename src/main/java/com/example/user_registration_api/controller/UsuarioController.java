package com.example.user_registration_api.controller;

import com.example.user_registration_api.model.Usuario;
import com.example.user_registration_api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registro")
    public ResponseEntity<?> register(@RequestBody Usuario usuario) {
        try {
            Usuario newUsuario = usuarioService.register(usuario);
            return ResponseEntity.status(201).body(newUsuario);
        } catch (Exception e) {
            return ResponseEntity.status(400)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"mensaje\": \"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        Optional<Usuario> usuario = usuarioService.findByEmail(email);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.status(404)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"mensaje\": \"Usuario no encontrado\"}");
        }
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsers() {
        List<Usuario> usuarios = usuarioService.findAll();
        return ResponseEntity.ok(usuarios);
    }
}
