package com.jobela.jobela_api.candidate.controller.candidate;


import com.jobela.jobela_api.candidate.dto.request.candidate.CreateCandidateRequest;
import com.jobela.jobela_api.candidate.dto.request.candidate.UpdateCandidateRequest;
import com.jobela.jobela_api.candidate.entity.Candidate;
import com.jobela.jobela_api.common.enums.Gender;
import com.jobela.jobela_api.common.enums.UserRole;
import com.jobela.jobela_api.config.IntegrationTestBase;
import com.jobela.jobela_api.user.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CandidateControllerIntegrationTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createCandidate_shouldReturnCreatedCandidate() throws Exception {
        var user = userRepository.save(
                User.builder()
                        .email("candidate@mail.com")
                        .password("password123")
                        .role(UserRole.CANDIDATE)
                        .build()
        );

        var request = new CreateCandidateRequest(
                null, null, "Peter", "Imrich", Gender.MALE, null, "Zurich",
                "Switzerland", "Slovak", LocalDate.of(1989,10, 25),
                "Koch", "Motivierte Koch", null);

        mockMvc.perform(post("/api/v1/candidates/user/{userId}", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(user.getId()))
                .andExpect(jsonPath("$.firstName").value("Peter"))
                .andExpect(jsonPath("$.lastName").value("Imrich"))
                .andExpect(jsonPath("$.gender").value("MALE"))
                .andExpect(jsonPath("$.city").value("Zurich"))
                .andExpect(jsonPath("$.country").value("Switzerland"))
                .andExpect(jsonPath("$.headline").value("Koch"));

        assertThat(candidateRepository.findAll()).hasSize(1);
        var seavedCandidate = candidateRepository.findAll().getFirst();
        assertThat(seavedCandidate.getUser().getId()).isEqualTo(user.getId());
        assertThat(seavedCandidate.getFirstName()).isEqualTo("Peter");
        assertThat(seavedCandidate.getCity()).isEqualTo("Zurich");
    }

    @Test
    void getCandidateById_shouldReturnCandidate() throws Exception {
        var user = userRepository.save(
                User.builder()
                        .email("candidate@mail.com")
                        .password("password123")
                        .role(UserRole.CANDIDATE)
                        .build()
        );

        var candidate = candidateRepository.save(
                Candidate.builder().
                        user(user)
                        .firstName("Peter")
                        .lastName("Imrich")
                        .gender(Gender.MALE)
                        .city("Zurich")
                        .country("Switzerland")
                        .build()
        );

        mockMvc.perform(get("/api/v1/candidates/{candidateId}", candidate.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(candidate.getId()))
                .andExpect(jsonPath("$.userId").value(user.getId()))
                .andExpect(jsonPath("$.firstName").value("Peter"))
                .andExpect(jsonPath("$.lastName").value("Imrich"))
                .andExpect(jsonPath("$.city").value("Zurich"));
    }

    @Test
    void getCandidateByUserId_shouldReturnCandidate() throws Exception{

        var user = userRepository.save(
                User.builder()
                        .email("candidate@mail.com")
                        .password("password123")
                        .role(UserRole.CANDIDATE)
                        .build()
        );

        candidateRepository.save(
                Candidate.builder()
                        .user(user)
                        .firstName("Peter")
                        .lastName("Imrich")
                        .gender(Gender.MALE)
                        .city("Bern")
                        .build()
        );

        mockMvc.perform(get("/api/v1/candidates/user/{userId}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Peter"))
                .andExpect(jsonPath("$.lastName").value("Imrich"))
                .andExpect(jsonPath("$.city").value("Bern"));
    }

    @Test
    void updateCandidate_shouldUpdateCandidate() throws Exception {
        var user = userRepository.save(
                User.builder()
                        .email("candidate@mail.com")
                        .password("password123")
                        .role(UserRole.CANDIDATE)
                        .build()
        );

        var candidate = candidateRepository.save(
                Candidate.builder()
                        .user(user)
                        .firstName("Peter")
                        .lastName("Imrich")
                        .city("Apenzell")
                        .headline("Old headline")
                        .build()
        );

        var request = new UpdateCandidateRequest(null, null, "Peter", "Imrich",
                null, null, "Zurich", null, null, null,
                "New headline", null, null);

        mockMvc.perform(patch("/api/v1/candidates/{candidateId}", candidate.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(candidate.getId()))
                .andExpect(jsonPath("$.city").value("Zurich"))
                .andExpect(jsonPath("$.headline").value("New headline"));

        var updatedCandidate = candidateRepository.findById(candidate.getId()).orElseThrow();

        assertThat(updatedCandidate.getCity()).isEqualTo("Zurich");
        assertThat(updatedCandidate.getHeadline()).isEqualTo("New headline");
    }

    @Test
    void deleteCandidate_shouldDeleteCandidate() throws Exception {
        var user = userRepository.save(
                User.builder()
                        .email("candidate@mail.com")
                        .password("password123")
                        .role(UserRole.CANDIDATE)
                        .build()
        );

        var candidate = candidateRepository.save(
                Candidate.builder()
                        .user(user)
                        .firstName("Peter")
                        .lastName("Imrich")
                        .build()
        );

        mockMvc.perform(delete("/api/v1/candidates/{candidateId}", candidate.getId()))
                .andExpect(status().isNoContent());

        assertThat(candidateRepository.findById(candidate.getId())).isEmpty();
    }

    @Test
    void getCandidateById_shouldReturnNotFoundWhenCandidateDoesNotExist() throws Exception {
        mockMvc.perform(get("/api/v1/candidates/{candidateId}", 999L))
                .andExpect(status().isNotFound());
    }
}
