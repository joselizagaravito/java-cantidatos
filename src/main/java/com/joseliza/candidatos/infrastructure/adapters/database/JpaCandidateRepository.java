package com.joseliza.candidatos.infrastructure.adapters.database;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCandidateRepository extends JpaRepository<CandidateEntity, Integer> {
}
