package com.joseliza.candidatos.application.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.joseliza.candidatos.domain.model.Candidate;
import com.joseliza.candidatos.domain.port.CandidateRepositoryPort;

public class CandidateService {
    private final CandidateRepositoryPort repository;

    public CandidateService(CandidateRepositoryPort repository) {
        this.repository = repository;
    }

    public List<Candidate> getAllCandidates() {
        return repository.findAll();
    }

}

