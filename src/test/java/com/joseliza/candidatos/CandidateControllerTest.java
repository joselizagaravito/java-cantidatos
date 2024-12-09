package com.joseliza.candidatos;

import com.joseliza.candidatos.domain.model.Candidate;
import com.joseliza.candidatos.infrastructure.adapters.api.CandidateController;
import com.joseliza.candidatos.infrastructure.adapters.security.JWTUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CandidateControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private JWTUtil jwtUtil;

	@Mock
	private com.joseliza.candidatos.application.usecases.GetCandidatesUseCase getCandidatesUseCase;

	@InjectMocks
	private CandidateController candidateController;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetAllCandidates() throws Exception {
		Candidate c1 = new Candidate(1, "Test User", "testuser@example.com", "M", 5000.0, "admin", null, null, null, 1,
				null);
		when(getCandidatesUseCase.execute()).thenReturn(Arrays.asList(c1));

		String token = jwtUtil.generateToken("admin");
		System.out.println("Generated Token: " + token);
		
		mockMvc.perform(get("/candidates").header("Authorization", "Bearer " + token)).andExpect(status().isOk())
				.andExpect(result -> System.out.println("Response: " + result.getResponse().getContentAsString()));
	}
}
