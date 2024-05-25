package com.example.user_registration_api.controller;

import com.example.user_registration_api.dto.UsuarioDto;
import com.example.user_registration_api.model.Usuario;
import com.example.user_registration_api.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@Api(value = "User Registration API", tags = {"Usuario"})
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registro")
    @ApiOperation(value = "Registrar un nuevo usuario", notes = "Crea un nuevo usuario en el sistema")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Usuario registrado exitosamente"),
            @ApiResponse(code = 400, message = "Error en el registro del usuario")
    })
    public ResponseEntity<?> register(
            @ApiParam(value = "Datos del usuario para registrar", required = true)
            @Valid @RequestBody UsuarioDto usuarioDto) {
        try {
            Usuario newUsuario = usuarioService.register(usuarioDto);
            return ResponseEntity.status(201).body(newUsuario);
        } catch (Exception e) {
            return ResponseEntity.status(400)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"mensaje\": \"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/{email}")
    @ApiOperation(value = "Obtener usuario por email", notes = "Devuelve el usuario correspondiente al email proporcionado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuario encontrado"),
            @ApiResponse(code = 404, message = "Usuario no encontrado")
    })
    public ResponseEntity<?> getUserByEmail(
            @ApiParam(value = "Email del usuario a buscar", required = true)
            @PathVariable String email) {
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
    @ApiOperation(value = "Obtener todos los usuarios", notes = "Devuelve una lista de todos los usuarios")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de usuarios obtenida exitosamente")
    })
    public ResponseEntity<List<Usuario>> getAllUsers() {
        List<Usuario> usuarios = usuarioService.findAll();
        return ResponseEntity.ok(usuarios);
    }
}
