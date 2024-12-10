package com.joseliza.candidatos.infrastructure.adapters.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JWTUtil jwtUtil;

    public SecurityConfig(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Desactiva CSRF para facilitar pruebas
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login").permitAll() // Permite acceso sin autenticación
                        .requestMatchers(HttpMethod.GET, "/candidates").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN") // Permite listar candidatos a USER y ADMIN
                        .requestMatchers(HttpMethod.POST, "/candidates").hasAuthority("ROLE_ADMIN") // Solo ADMIN puede agregar
                        .requestMatchers(HttpMethod.PUT, "/candidates/**").hasAuthority("ROLE_ADMIN") // Solo ADMIN puede actualizar
                        .anyRequest().authenticated() // Resto de endpoints requiere autenticación
                )
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter(jwtUtil);
    }
}
