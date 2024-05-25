package com.example.user_registration_api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtUtil = new JwtUtil();
        jwtUtil.setSecret("secretKey");
    }

    @Test
    void generateToken_success() {
        String email = "test@example.com";
        String token = jwtUtil.generateToken(email);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void extractAllClaims_success() {
        String email = "test@example.com";
        String token = jwtUtil.generateToken(email);

        Claims claims = jwtUtil.extractAllClaims(token);

        assertNotNull(claims);
        assertEquals(email, claims.getSubject());
    }

    @Test
    void isTokenExpired_success() {
        String email = "test@example.com";
        String token = jwtUtil.generateToken(email);

        assertFalse(jwtUtil.isTokenExpired(token));

        // Crear manualmente un token expirado
        String expiredToken = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24))
                .setExpiration(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 23))
                .signWith(SignatureAlgorithm.HS256, "secretKey")
                .compact();

        // Verificar que el token esté expirado y lanzar la excepción
        assertTrue(jwtUtil.isTokenExpired(expiredToken));
    }

    @Test
    void extractEmail_success() {
        String email = "test@example.com";
        String token = jwtUtil.generateToken(email);

        String extractedEmail = jwtUtil.extractEmail(token);

        assertEquals(email, extractedEmail);
    }
}
