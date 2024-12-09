package com.joseliza.candidatos.infrastructure.adapters.database;

import org.springframework.stereotype.Component;

import com.joseliza.candidatos.domain.model.Candidate;
import com.joseliza.candidatos.domain.port.CandidateRepositoryPort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CandidateRepositoryAdapter implements CandidateRepositoryPort {

    private final JpaCandidateRepository jpaRepo;

    public CandidateRepositoryAdapter(JpaCandidateRepository jpaRepo) {
        this.jpaRepo = jpaRepo;
    }
    
    @Override
    public List<Candidate> findAll() {
        List<CandidateEntity> entities = jpaRepo.findAll();
        List<Candidate> candidates = new ArrayList<>();
        for (CandidateEntity entity : entities) {
            candidates.add(toDomain(entity));
        }
        return candidates;
    }

    @Override
    public Optional<Candidate> findById(Integer id) {
        return jpaRepo.findById(id).map(this::toDomain);
    }

    @Override
    public Candidate save(Candidate candidate) {
        CandidateEntity entity = toEntity(candidate);
        return toDomain(jpaRepo.save(entity));
    }

    @Override
    public void deleteById(Integer id) {
        jpaRepo.deleteById(id);
    }

    private Candidate toDomain(CandidateEntity e) {
        Candidate c = new Candidate();
        c.setId(e.getId());
        c.setName(e.getName());
        c.setEmail(e.getEmail());
        c.setGender(e.getGender().name());
        c.setExpectedSalary(e.getExpectedSalary());
        c.setCreatedBy(e.getCreatedBy());
        c.setCreatedAt(e.getCreatedAt());
        c.setUpdatedBy(e.getUpdatedBy());
        c.setUpdatedAt(e.getUpdatedAt());
        c.setRowstate(e.getRowstate());
        c.setLastupdate(e.getLastupdate());
        return c;
    }

    private CandidateEntity toEntity(Candidate c) {
        CandidateEntity e = new CandidateEntity();
        e.setId(c.getId());
        e.setName(c.getName());
        e.setEmail(c.getEmail());
        e.setGender(CandidateEntity.Gender.valueOf(c.getGender()));
        e.setExpectedSalary(c.getExpectedSalary());
        e.setCreatedBy(c.getCreatedBy());
        e.setCreatedAt(c.getCreatedAt());
        e.setUpdatedBy(c.getUpdatedBy());
        e.setUpdatedAt(c.getUpdatedAt());
        e.setRowstate(c.getRowstate());
        e.setLastupdate(c.getLastupdate());
        return e;
    }
}
