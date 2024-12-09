package com.joseliza.candidatos.infrastructure.adapters.api;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.joseliza.candidatos.infrastructure.adapters.security.JWTUtil;

import lombok.Data;

@RestController
public class AuthController {

    private final JWTUtil jwtUtil;

    public AuthController(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/auth/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        if("admin".equals(loginRequest.getUsername()) && "admin".equals(loginRequest.getPassword())){
            return jwtUtil.generateToken(loginRequest.getUsername());
        }
        throw new RuntimeException("Usuario o clave inválidos");
    }

    @Data
    static class LoginRequest {
        private String username;
        private String password;
    }
}