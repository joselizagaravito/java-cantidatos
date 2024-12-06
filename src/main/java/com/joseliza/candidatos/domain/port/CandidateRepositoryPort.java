package com.joseliza.candidatos.domain.port;

import java.util.List;
import java.util.Optional;

import com.joseliza.candidatos.domain.model.Candidate;

public interface CandidateRepositoryPort {
    List<Candidate> findAll();
    Optional<Candidate> findById(Integer id);
    Candidate save(Candidate candidate);
    void deleteById(Integer id);
}
