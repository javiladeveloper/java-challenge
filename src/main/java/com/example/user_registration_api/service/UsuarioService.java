package com.example.user_registration_api.service;

import com.example.user_registration_api.dto.UsuarioDto;
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

    public Usuario register(UsuarioDto usuarioDto) throws Exception {
        logger.info("Attempting to register user with email: {}", usuarioDto.getEmail());

        if (usuarioRepository.findByEmail(usuarioDto.getEmail()).isPresent()) {
            logger.warn("User with email {} is already registered", usuarioDto.getEmail());
            throw new Exception("El correo ya registrado");
        }

        Usuario usuario = convertToEntity(usuarioDto);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setToken(jwtUtil.generateToken(usuario.getEmail()));
        Usuario savedUsuario = usuarioRepository.save(usuario);
        logger.info("User registered successfully with email: {}", usuario.getEmail());
        return savedUsuario;
    }

    private Usuario convertToEntity(UsuarioDto usuarioDto) {
        Usuario usuario = new Usuario();
        usuario.setName(usuarioDto.getName());
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setPassword(usuarioDto.getPassword());
        usuario.setPhones(usuarioDto.getPhones());
        return usuario;
    }

    public Optional<Usuario> findByEmail(String email) {
        logger.debug("Finding user by email: {}", email);
        return usuarioRepository.findByEmail(email);
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }
}
