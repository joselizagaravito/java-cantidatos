package com.joseliza.candidatos.infrastructure.adapters.api;

import com.joseliza.candidatos.application.usecases.GetCandidatesUseCase;
import com.joseliza.candidatos.domain.model.Candidate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Candidatos", description = "Gestión de candidatos.")
public class CandidateController {

	private final GetCandidatesUseCase getCandidatesUseCase;

	public CandidateController(GetCandidatesUseCase getCandidatesUseCase) {
		this.getCandidatesUseCase = getCandidatesUseCase;
	}

	@GetMapping("/candidates")
	@Operation(summary = "Obtener todos los candidatos", description = "Devuelve una lista con todos los candidatos registrados.", responses = {
			@ApiResponse(responseCode = "200", description = "Lista de candidatos retornada exitosamente."),
			@ApiResponse(responseCode = "401", description = "No autorizado. Se requiere un token JWT válido."),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor.") })
	public List<Candidate> getAll() {
		return getCandidatesUseCase.execute();
	}
}
