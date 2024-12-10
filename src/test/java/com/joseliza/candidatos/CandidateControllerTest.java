package com.joseliza.candidatos;

import com.joseliza.candidatos.domain.model.Candidate;
import com.joseliza.candidatos.infrastructure.adapters.api.CandidateController;
import com.joseliza.candidatos.infrastructure.adapters.security.JWTAuthenticationFilter;
import com.joseliza.candidatos.infrastructure.adapters.security.JWTUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CandidateController.class)
public class CandidateControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockBean
    private JWTUtil jwtUtil;

    @MockBean
    private com.joseliza.candidatos.application.usecases.GetCandidatesUseCase getCandidatesUseCase;

    @MockBean
    private com.joseliza.candidatos.application.usecases.SaveCandidateUseCase saveCandidateUseCase;

    @MockBean
    private com.joseliza.candidatos.application.usecases.UpdateCandidateUseCase updateCandidateUseCase;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilter(new JWTAuthenticationFilter(jwtUtil))
                .build();
    }

    @Test
    void testGetAllCandidates() throws Exception {
        Candidate c1 = new Candidate(1, "Test User", "testuser@example.com", "M", 5000.0, "admin", null, null, null, 1, null);
        when(getCandidatesUseCase.execute()).thenReturn(Arrays.asList(c1));

        String mockToken = "mock_token";
        when(jwtUtil.validateToken(mockToken)).thenReturn(true);
        when(jwtUtil.getUsername(mockToken)).thenReturn("admin");
        when(jwtUtil.getAuthentication(mockToken)).thenReturn(
                new UsernamePasswordAuthenticationToken("admin", null, List.of(new SimpleGrantedAuthority("ROLE_ADMIN")))
        );

        mockMvc.perform(get("/candidates")
                        .header("Authorization", "Bearer " + mockToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test User"))
                .andExpect(jsonPath("$[0].email").value("testuser@example.com"))
                .andExpect(jsonPath("$[0].expectedSalary").value(5000.0));
    }
}
