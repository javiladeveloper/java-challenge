package com.example.user_registration_api.service;

import com.example.user_registration_api.dto.UsuarioDto;
import com.example.user_registration_api.model.Usuario;
import com.example.user_registration_api.repository.UsuarioRepository;
import com.example.user_registration_api.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_success() throws Exception {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setEmail("juan@rodriguez.org");
        usuarioDto.setPassword("hunter2");

        Usuario usuario = new Usuario();
        usuario.setEmail("juan@rodriguez.org");
        usuario.setPassword("encodedPassword");
        usuario.setToken("jwtToken"); // Asegúrate de que el token esté configurado

        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(jwtUtil.generateToken(anyString())).thenReturn("jwtToken");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> {
            Usuario savedUsuario = invocation.getArgument(0);
            savedUsuario.setToken("jwtToken"); // Configura el token antes de devolverlo
            return savedUsuario;
        });

        Usuario savedUsuario = usuarioService.register(usuarioDto);

        assertNotNull(savedUsuario);
        assertEquals("encodedPassword", savedUsuario.getPassword());
        assertEquals("jwtToken", savedUsuario.getToken());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void register_emailAlreadyExists() {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setEmail("juan@rodriguez.org");

        Usuario usuario = new Usuario();
        usuario.setEmail("juan@rodriguez.org");

        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(usuario));

        Exception exception = assertThrows(Exception.class, () -> {
            usuarioService.register(usuarioDto);
        });

        assertEquals("El correo ya registrado", exception.getMessage());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void findByEmail_success() {
        Usuario usuario = new Usuario();
        usuario.setEmail("juan@rodriguez.org");

        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(usuario));

        Optional<Usuario> foundUsuario = usuarioService.findByEmail("juan@rodriguez.org");

        assertTrue(foundUsuario.isPresent());
        assertEquals("juan@rodriguez.org", foundUsuario.get().getEmail());
    }

    @Test
    void findByEmail_notFound() {
        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        Optional<Usuario> foundUsuario = usuarioService.findByEmail("noexist@domain.com");

        assertFalse(foundUsuario.isPresent());
    }

    @Test
    void findAll_success() {
        List<Usuario> usuarios = List.of(new Usuario(), new Usuario());
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<Usuario> foundUsuarios = usuarioService.findAll();

        assertEquals(2, foundUsuarios.size());
    }
}
