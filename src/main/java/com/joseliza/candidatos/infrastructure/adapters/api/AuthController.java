package com.joseliza.candidatos.infrastructure.adapters.api;

import com.joseliza.candidatos.infrastructure.adapters.security.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Autenticación", description = "Endpoints relacionados con la autenticación y generación de JWT.")
public class AuthController {

	private final JWTUtil jwtUtil;

	public AuthController(JWTUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@PostMapping("/auth/login")
	@Operation(summary = "Generar token JWT", description = "Autentica al usuario y genera un token JWT.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Ejemplo de login", value = "{\"username\": \"admin\", \"password\": \"admin\""))), responses = {
			@ApiResponse(responseCode = "200", description = "Token generado exitosamente.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
			@ApiResponse(responseCode = "401", description = "Usuario o clave inválidos.") })
	public String login(@RequestBody LoginRequest loginRequest) {
		if ("admin".equals(loginRequest.getUsername()) && "admin".equals(loginRequest.getPassword())) {
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
