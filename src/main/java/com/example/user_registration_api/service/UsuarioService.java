package com.example.user_registration_api.service;

import com.example.user_registration_api.model.Usuario;
import com.example.user_registration_api.repository.UsuarioRepository;
import com.example.user_registration_api.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario register(Usuario usuario) throws Exception {
        logger.info("Attempting to register user with email: {}", usuario.getEmail());

        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            logger.warn("User with email {} is already registered", usuario.getEmail());
            throw new Exception("El correo ya registrado");
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword())); // Codificar la contraseña
        usuario.setToken(jwtUtil.generateToken(usuario.getEmail()));
        Usuario savedUsuario = usuarioRepository.save(usuario);
        logger.info("User registered successfully with email: {}", usuario.getEmail());
        return savedUsuario;
    }


    public Optional<Usuario> findByEmail(String email) {
        logger.debug("Finding user by email: {}", email);
        return usuarioRepository.findByEmail(email);
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }
}
