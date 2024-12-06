package com.joseliza.candidatos.infrastructure.adapters.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joseliza.candidatos.application.usecases.GetCandidatesUseCase;
import com.joseliza.candidatos.domain.model.Candidate;

import java.util.List;

@RestController
public class CandidateController {

    private final GetCandidatesUseCase getCandidatesUseCase;

    public CandidateController(GetCandidatesUseCase getCandidatesUseCase) {
        this.getCandidatesUseCase = getCandidatesUseCase;
    }

    @GetMapping("/candidates")
    public List<Candidate> getAll() {
        return getCandidatesUseCase.execute();
    }
}
