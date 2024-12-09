package com.joseliza.candidatos;

import com.joseliza.candidatos.application.usecases.GetCandidatesUseCase;
import com.joseliza.candidatos.domain.model.Candidate;
import com.joseliza.candidatos.domain.port.CandidateRepositoryPort;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

public class GetCandidatesUseCaseTest {

    @Test
    void testExecute() {
        CandidateRepositoryPort mockRepo = Mockito.mock(CandidateRepositoryPort.class);

        Candidate c1 = new Candidate(1, "José Liza", "joseliza@gmail.com", "M", 8500.00, "admin", null, null, null, 1, null);
        Candidate c2 = new Candidate(2, "María Lopez", "maria.lopez@gmail.com", "F", 8000.00, "admin", null, null, null, 1, null);

        Mockito.when(mockRepo.findAll()).thenReturn(Arrays.asList(c1, c2));

        GetCandidatesUseCase useCase = new GetCandidatesUseCase(mockRepo);
        List<Candidate> result = useCase.execute();

        assertEquals(2, result.size());
        assertEquals("José Liza", result.get(0).getName());
    }
}
