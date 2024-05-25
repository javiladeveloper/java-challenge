package com.example.user_registration_api.controller;

import com.example.user_registration_api.dto.UsuarioDto;
import com.example.user_registration_api.model.Usuario;
import com.example.user_registration_api.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UsuarioControllerTest {

    @InjectMocks
    private UsuarioController usuarioController;

    @Mock
    private UsuarioService usuarioService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
    }

    @Test
    void register_success() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("juan@rodriguez.org");

        when(usuarioService.register(any(UsuarioDto.class))).thenReturn(usuario);

        mockMvc.perform(post("/api/usuarios/registro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Juan Rodriguez\",\"email\":\"juan@rodriguez.org\",\"password\":\"hunter2\",\"phones\":[{\"number\":\"1234567\",\"citycode\":\"1\",\"countrycode\":\"57\"}]}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void register_emailAlreadyExists() throws Exception {
        when(usuarioService.register(any(UsuarioDto.class))).thenThrow(new Exception("El correo ya registrado"));

        mockMvc.perform(post("/api/usuarios/registro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Juan Rodriguez\",\"email\":\"juan@rodriguez.org\",\"password\":\"hunter2\",\"phones\":[{\"number\":\"1234567\",\"citycode\":\"1\",\"countrycode\":\"57\"}]}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"mensaje\": \"El correo ya registrado\"}"));
    }

    @Test
    void getUserByEmail_success() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("juan@rodriguez.org");

        when(usuarioService.findByEmail(anyString())).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/api/usuarios/juan@rodriguez.org")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getUserByEmail_notFound() throws Exception {
        when(usuarioService.findByEmail(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/usuarios/noexist@domain.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"mensaje\": \"Usuario no encontrado\"}"));
    }

    @Test
    void getAllUsers_success() throws Exception {
        mockMvc.perform(get("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
