package com.joseliza.candidatos;

import com.joseliza.candidatos.application.usecases.UpdateCandidateUseCase;
import com.joseliza.candidatos.domain.model.Candidate;
import com.joseliza.candidatos.domain.port.CandidateRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UpdateCandidateUseCaseTest {

    @Mock
    private CandidateRepositoryPort repository;

    @InjectMocks
    private UpdateCandidateUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateCandidate() {
        Candidate existingCandidate = new Candidate(1, "John Doe", "john.doe@gmail.com", "M", 5000.0,
                "admin", new Date(), null, null, 1, new Date());
        Candidate updatedCandidate = new Candidate(1, "John Updated", "john.doe@gmail.com", "M", 6000.0,
                null, null, "editor", new Date(), 1, null);

        when(repository.findById(1)).thenReturn(Optional.of(existingCandidate));
        when(repository.save(any(Candidate.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Candidate result = useCase.execute(updatedCandidate);

        assertEquals("John Updated", result.getName());
        assertEquals(6000.0, result.getExpectedSalary());
        assertEquals("admin", result.getCreatedBy());
        assertEquals(existingCandidate.getCreatedAt(), result.getCreatedAt());
        verify(repository, times(1)).findById(1);
        verify(repository, times(1)).save(any(Candidate.class));
    }
}
