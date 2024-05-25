package com.example.user_registration_api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/usuarios/registro").permitAll() // Permitir acceso sin autenticación para registrar usuarios
                .antMatchers("/api/usuarios/**").permitAll() // Permitir acceso sin autenticación a los endpoints GET
                .antMatchers("/h2-console/**").permitAll() // Permitir acceso sin autenticación a la consola H2
                .antMatchers("/swagger-ui/**").permitAll() // Permitir acceso sin autenticación al swagger
                .antMatchers("/swagger-resources/**").permitAll() // Permitir acceso sin autenticación a los recursos de swagger
                .antMatchers("/v2/api-docs").permitAll() // Permitir acceso sin autenticación a la documentación de la API
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().sameOrigin(); // Permitir que la consola H2 se cargue correctamente en un iframe
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
