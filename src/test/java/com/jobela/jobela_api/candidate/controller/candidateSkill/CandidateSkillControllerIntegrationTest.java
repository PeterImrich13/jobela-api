package com.jobela.jobela_api.candidate.controller.candidateSkill;


import com.jobela.jobela_api.candidate.dto.request.skill.CreateCandidateSkillRequest;
import com.jobela.jobela_api.candidate.dto.request.skill.UpdateCandidateSkillRequest;
import com.jobela.jobela_api.candidate.entity.Candidate;
import com.jobela.jobela_api.candidate.entity.CandidateSkill;
import com.jobela.jobela_api.common.enums.SkillLevel;
import com.jobela.jobela_api.common.enums.SkillType;
import com.jobela.jobela_api.common.enums.UserRole;
import com.jobela.jobela_api.config.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CandidateSkillControllerIntegrationTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createSkill_shouldCreateSkill() throws Exception {
        var user = createAuthenticatedUser(UserRole.CANDIDATE);
        var token = bearerTokenForUser(user);

        var candidate = candidateRepository.save(
                Candidate.builder()
                        .user(user)
                        .firstName("Peter")
                        .lastName("Imrich")
                        .build()
        );

        var request = new CreateCandidateSkillRequest("Java", SkillType.HARD, SkillLevel.ADVANCED);

        mockMvc.perform(post("/api/v1/candidates/{candidateId}/skills", candidate.getId())
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.candidateId").value(candidate.getId()))
                .andExpect(jsonPath("$.skillName").value("Java"))
                .andExpect(jsonPath("$.skillType").value("HARD"))
                .andExpect(jsonPath("$.level").value("ADVANCED"));

        assertThat(candidateSkillRepository.findAll()).hasSize(1);
    }

    @Test
    void getSkills_shouldReturnAllSkills() throws Exception {
        var user = createAuthenticatedUser(UserRole.CANDIDATE);
        var token = bearerTokenForUser(user);

        var candidate = candidateRepository.save(
                Candidate.builder()
                        .user(user)
                        .firstName("Peter")
                        .lastName("Imrich")
                        .build()
        );

        candidateSkillRepository.save(
                CandidateSkill.builder()
                        .candidate(candidate)
                        .skillName("Java")
                        .skillType(SkillType.HARD)
                        .level(SkillLevel.ADVANCED)
                        .build()
        );

        candidateSkillRepository.save(
                CandidateSkill.builder()
                        .candidate(candidate)
                        .skillName("Communication")
                        .skillType(SkillType.SOFT)
                        .level(SkillLevel.INTERMEDIATE)
                        .build()
        );

        mockMvc.perform(get("/api/v1/candidates/{candidateId}/skills", candidate.getId())
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getSkills_shouldFilterByType() throws Exception {
        var user = createAuthenticatedUser(UserRole.CANDIDATE);
        var token = bearerTokenForUser(user);

        var candidate = candidateRepository.save(
                Candidate.builder()
                        .user(user)
                        .firstName("Peter")
                        .lastName("Imrich")
                        .build()
        );

        candidateSkillRepository.save(
                CandidateSkill.builder()
                        .candidate(candidate)
                        .skillName("Java")
                        .skillType(SkillType.HARD)
                        .level(SkillLevel.ADVANCED)
                        .build()
        );

        candidateSkillRepository.save(
                CandidateSkill.builder()
                        .candidate(candidate)
                        .skillName("Communication")
                        .skillType(SkillType.SOFT)
                        .level(SkillLevel.INTERMEDIATE)
                        .build()
        );

        mockMvc.perform(get("/api/v1/candidates/{candidateId}/skills", candidate.getId())
                        .header("Authorization", token)
                        .param("type", "HARD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].skillName").value("Java"))
                .andExpect(jsonPath("$[0].skillType").value("HARD"));
    }

    @Test
    void getSkillById_shouldReturnSkill() throws Exception {
        var user = createAuthenticatedUser(UserRole.CANDIDATE);
        var token = bearerTokenForUser(user);

        var candidate = candidateRepository.save(
                Candidate.builder()
                        .user(user)
                        .firstName("Peter")
                        .lastName("Imrich")
                        .build()
        );

        var skill = candidateSkillRepository.save(
                CandidateSkill.builder()
                        .candidate(candidate)
                        .skillName("Java")
                        .skillType(SkillType.HARD)
                        .level(SkillLevel.ADVANCED)
                        .build()
        );

        mockMvc.perform(get("/api/v1/candidates/{candidateId}/skills/{skillId}",
                candidate.getId(), skill.getId())
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(skill.getId()))
                .andExpect(jsonPath("$.candidateId").value(candidate.getId()))
                .andExpect(jsonPath("$.skillName").value("Java"))
                .andExpect(jsonPath("$.skillType").value("HARD"))
                .andExpect(jsonPath("$.level").value("ADVANCED"));
    }

        @Test
    void updateSkill_shouldUpdateSkill() throws Exception {
        var user = createAuthenticatedUser(UserRole.CANDIDATE);
            var token = bearerTokenForUser(user);

            var candidate = candidateRepository.save(
                Candidate.builder()
                        .user(user)
                        .firstName("Peter")
                        .lastName("Imrich")
                        .build()
        );

        var skill = candidateSkillRepository.save(
                CandidateSkill.builder()
                        .candidate(candidate)
                        .skillName("Java")
                        .skillType(SkillType.HARD)
                        .level(SkillLevel.INTERMEDIATE)
                        .build()
        );

        var request = new UpdateCandidateSkillRequest(
                "Spring Boot", SkillType.HARD, SkillLevel.ADVANCED);

        mockMvc.perform(patch("/api/v1/candidates/{candidateId}/skills/{skillId}",
                candidate.getId(), skill.getId())
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.skillName").value("Spring Boot"))
                .andExpect(jsonPath("$.skillType").value("HARD"))
                .andExpect(jsonPath("$.level").value("ADVANCED"));

        var updatedSkill = candidateSkillRepository.findById(skill.getId()).orElseThrow();
        assertThat(updatedSkill.getSkillName()).isEqualTo("Spring Boot");
        assertThat(updatedSkill.getLevel()).isEqualTo(SkillLevel.ADVANCED);
    }

    @Test
    void deleteSkill_shouldDeleteSkill() throws  Exception {
        var user = createAuthenticatedUser(UserRole.CANDIDATE);
        var token = bearerTokenForUser(user);


        var candidate = candidateRepository.save(
                Candidate.builder()
                        .user(user)
                        .firstName("Peter")
                        .lastName("Imrich")
                        .build()
        );

        var skill = candidateSkillRepository.save(
                CandidateSkill.builder()
                        .candidate(candidate)
                        .skillName("Java")
                        .skillType(SkillType.HARD)
                        .level(SkillLevel.ADVANCED)
                        .build()
        );

        mockMvc.perform(delete("/api/v1/candidates/{candidateId}/skills/{skillId}",
                candidate.getId(), skill.getId())
                        .header("Authorization", token))
                .andExpect(status().isNoContent());

        assertThat(candidateSkillRepository.findById(skill.getId())).isEmpty();
    }

    @Test
    void getSkillById_shouldReturnNotFoundWhenSkillDoesNotExist() throws Exception {
        var user = createAuthenticatedUser(UserRole.CANDIDATE);
        var token = bearerTokenForUser(user);

        var candidate = candidateRepository.save(
                Candidate.builder()
                        .user(user)
                        .firstName("Peter")
                        .lastName("Imrich")
                        .build()
        );

        mockMvc.perform(get("/api/v1/candidates/{candidateId}/skills/{skillId}",
                candidate.getId(), 999L)
                        .header("Authorization", token))
                .andExpect(status().isNotFound());
    }
 }
