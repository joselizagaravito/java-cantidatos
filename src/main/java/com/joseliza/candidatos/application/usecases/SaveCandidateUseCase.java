package com.joseliza.candidatos.application.usecases;

import com.joseliza.candidatos.domain.model.Candidate;
import com.joseliza.candidatos.domain.port.CandidateRepositoryPort;

import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class SaveCandidateUseCase {

    private final CandidateRepositoryPort repository;

    public SaveCandidateUseCase(CandidateRepositoryPort repository) {
        this.repository = repository;
    }

    public Candidate execute(Candidate candidate) {
    	if (candidate.getRowstate() == null) {
            candidate.setRowstate(1);
        }
    	candidate.setLastupdate(new Date());
        return repository.save(candidate);
    }
}
