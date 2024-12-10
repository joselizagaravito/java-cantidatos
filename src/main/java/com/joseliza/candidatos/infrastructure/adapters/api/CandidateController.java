package com.joseliza.candidatos.infrastructure.adapters.api;

import com.joseliza.candidatos.application.usecases.GetCandidatesUseCase;
import com.joseliza.candidatos.application.usecases.SaveCandidateUseCase;
import com.joseliza.candidatos.application.usecases.UpdateCandidateUseCase;
import com.joseliza.candidatos.domain.model.Candidate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Candidatos", description = "Gestión de candidatos.")
public class CandidateController {

    private final GetCandidatesUseCase getCandidatesUseCase;
    private final SaveCandidateUseCase saveCandidateUseCase;
    private final UpdateCandidateUseCase updateCandidateUseCase;

    public CandidateController(GetCandidatesUseCase getCandidatesUseCase, 
                                SaveCandidateUseCase saveCandidateUseCase,
                                UpdateCandidateUseCase updateCandidateUseCase) {
        this.getCandidatesUseCase = getCandidatesUseCase;
        this.saveCandidateUseCase = saveCandidateUseCase;
        this.updateCandidateUseCase = updateCandidateUseCase;
    }

    @GetMapping("/candidates")
    @Operation(summary = "Obtener todos los candidatos", description = "Devuelve una lista con todos los candidatos registrados.", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de candidatos retornada exitosamente."),
            @ApiResponse(responseCode = "401", description = "No autorizado. Se requiere un token JWT válido."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.") })
    public List<Candidate> getAll() {
        return getCandidatesUseCase.execute();
    }

    @PostMapping("/candidates")
    @Operation(summary = "Agregar un nuevo candidato", description = "Registra un nuevo candidato en el sistema.", responses = {
            @ApiResponse(responseCode = "201", description = "Candidato registrado exitosamente."),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.") })
    public Candidate createCandidate(@RequestBody Candidate candidate) {
        return saveCandidateUseCase.execute(candidate);
    }

    @PutMapping("/candidates/{id}")
    @Operation(summary = "Actualizar un candidato", description = "Actualiza los datos de un candidato existente.", responses = {
            @ApiResponse(responseCode = "200", description = "Candidato actualizado exitosamente."),
            @ApiResponse(responseCode = "404", description = "Candidato no encontrado."),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.") })
    public Candidate updateCandidate(@PathVariable Integer id, @RequestBody Candidate candidate) {
        candidate.setId(id);
        return updateCandidateUseCase.execute(candidate);
    }
}
