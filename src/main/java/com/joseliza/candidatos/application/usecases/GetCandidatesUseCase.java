package com.joseliza.candidatos.application.usecases;

import java.util.List;

import org.springframework.stereotype.Service;

import com.joseliza.candidatos.domain.model.Candidate;
import com.joseliza.candidatos.domain.port.CandidateRepositoryPort;

@Service
public class GetCandidatesUseCase {
    private final CandidateRepositoryPort repository;

    public GetCandidatesUseCase(CandidateRepositoryPort repository) {
        this.repository = repository;
    }

    public List<Candidate> execute() {
        return repository.findAll();
    }
}
