package com.joseliza.candidatos.application.usecases;

import com.joseliza.candidatos.domain.model.Candidate;
import com.joseliza.candidatos.domain.port.CandidateRepositoryPort;

import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class UpdateCandidateUseCase {

    private final CandidateRepositoryPort repository;

    public UpdateCandidateUseCase(CandidateRepositoryPort repository) {
        this.repository = repository;
    }

    public Candidate execute(Candidate candidate) {
        Candidate existingCandidate = repository.findById(candidate.getId())
                .orElseThrow(() -> new RuntimeException("Candidato no encontrado con ID: " + candidate.getId()));
        candidate.setCreatedAt(existingCandidate.getCreatedAt());
        candidate.setCreatedBy(existingCandidate.getCreatedBy());

        candidate.setLastupdate(new Date());

        return repository.save(candidate);
    }
}
