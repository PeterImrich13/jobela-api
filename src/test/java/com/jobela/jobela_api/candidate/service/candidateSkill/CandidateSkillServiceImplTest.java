package com.jobela.jobela_api.candidate.service.candidateSkill;


import com.jobela.jobela_api.candidate.dto.request.skill.CreateCandidateSkillRequest;
import com.jobela.jobela_api.candidate.dto.request.skill.UpdateCandidateSkillRequest;
import com.jobela.jobela_api.candidate.dto.response.skill.CandidateSkillResponse;
import com.jobela.jobela_api.candidate.entity.Candidate;
import com.jobela.jobela_api.candidate.entity.CandidateSkill;
import com.jobela.jobela_api.candidate.mapper.CandidateSkillMapper;
import com.jobela.jobela_api.candidate.repository.CandidateRepository;
import com.jobela.jobela_api.candidate.repository.CandidateSkillRepository;
import com.jobela.jobela_api.candidate.service.skill.CandidateSkillServiceImpl;
import com.jobela.jobela_api.common.enums.SkillLevel;
import com.jobela.jobela_api.common.enums.SkillType;
import com.jobela.jobela_api.common.exception.CandidateNotFoundException;
import com.jobela.jobela_api.common.exception.CandidateSkillAlreadyExistsException;
import com.jobela.jobela_api.common.exception.SkillNotFoundException;
import com.jobela.jobela_api.common.mapper.StringMapperHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CandidateSkillServiceImplTest {

    @Mock
    private CandidateSkillRepository candidateSkillRepository;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private CandidateSkillMapper candidateSkillMapper;

    @Mock
    private StringMapperHelper stringMapperHelper;

    @InjectMocks
    private CandidateSkillServiceImpl candidateSkillService;

    @Test
    void createSkill_ShouldCreateSkillSuccessfully() {
        var candidate = Candidate.builder().id(1L).build();

        var request = new CreateCandidateSkillRequest("Java", SkillType.HARD, SkillLevel.ADVANCED);

        var skill = CandidateSkill.builder()
                .skillName("Java")
                .skillType(SkillType.HARD)
                .level(SkillLevel.ADVANCED)
                .build();

        var response = new CandidateSkillResponse(10L, 1L, "Java", SkillType.HARD,
                SkillLevel.ADVANCED, null);

        when(candidateRepository.findById(1L)).thenReturn(Optional.of(candidate));
        when(stringMapperHelper.clean("Java")).thenReturn("Java");
        when(candidateSkillRepository.existsByCandidateIdAndSkillNameIgnoreCaseAndSkillType(
                1L, "Java", SkillType.HARD)).thenReturn(false);
        when(candidateSkillMapper.toEntity(request)).thenReturn(skill);
        when(candidateSkillRepository.save(skill)).thenReturn(skill);
        when(candidateSkillMapper.toResponse(skill)).thenReturn(response);

        var result = candidateSkillService.createSkill(1L, request);

        assertThat(result.skillName()).isEqualTo("Java");

        verify(candidateSkillRepository).save(skill);
    }

    @Test
    void createSkill_shouldThrowWhenCandidateNotFound() {
        var request = new CreateCandidateSkillRequest("Java", SkillType.HARD, SkillLevel.ADVANCED);

        when(candidateRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> candidateSkillService.createSkill(1L, request))
                .isInstanceOf(CandidateNotFoundException.class);
    }

    @Test
    void createSkill_shouldThrownWhenSkillAlreadyExists() {
        var candidate = Candidate.builder().id(1L).build();

        var request = new CreateCandidateSkillRequest("Java", SkillType.HARD, SkillLevel.ADVANCED);

        when(candidateRepository.findById(1L)).thenReturn(Optional.of(candidate));
        when(stringMapperHelper.clean("Java")).thenReturn("Java");

        when(candidateSkillRepository.existsByCandidateIdAndSkillNameIgnoreCaseAndSkillType(
                1L, "Java", SkillType.HARD)).thenReturn(true);

        assertThatThrownBy(() -> candidateSkillService.createSkill(1L, request))
                .isInstanceOf(CandidateSkillAlreadyExistsException.class);
    }

    @Test
    void getSkillById_shouldThrownWhenSkillNotFound() {
        var candidate = Candidate.builder().id(1L).build();

        when(candidateRepository.findById(1L)).thenReturn(Optional.of(candidate));
        when(candidateSkillRepository.findByIdAndCandidateId(99L, 1L)).thenReturn(Optional.empty());

        assertThatThrownBy(()-> candidateSkillService.getSkillById(1L, 99L))
                .isInstanceOf(SkillNotFoundException.class);
    }

    @Test
    void getSkills_shouldReturnSkills() {
        var candidate = Candidate.builder().id(1L).build();

        var skill = CandidateSkill.builder()
                .id(10L)
                .skillName("Java")
                .skillType(SkillType.HARD)
                .level(SkillLevel.ADVANCED)
                .build();

        var response = new CandidateSkillResponse(
                10L, 1L, "Java", SkillType.HARD, SkillLevel.ADVANCED, null);

        when(candidateRepository.findById(1L)).thenReturn(Optional.of(candidate));
        when(candidateSkillRepository.findAllByCandidateId(1L)).thenReturn(List.of(skill));

        when(candidateSkillMapper.toResponse(skill)).thenReturn(response);

        var result = candidateSkillService.getSkills(1L, null);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().skillName()).isEqualTo("Java");
    }

    @Test
    void updateSkill_shouldUpdateSkillSuccessfully() {
        var candidate = Candidate.builder().id(1L).build();

        var skill = CandidateSkill.builder()
                .id(10L)
                .skillName("Java")
                .skillType(SkillType.HARD)
                .level(SkillLevel.INTERMEDIATE)
                .build();

        var request = new  UpdateCandidateSkillRequest("Spring Boot", SkillType.HARD, SkillLevel.ADVANCED);

        var response = new CandidateSkillResponse(
                10L , 1L, "Spring Boot", SkillType.HARD, SkillLevel.ADVANCED, null);

        when(candidateRepository.findById(1L)).thenReturn(Optional.of(candidate));
        when(candidateSkillRepository.findByIdAndCandidateId(10L, 1L)).thenReturn(Optional.of(skill));
        when(stringMapperHelper.clean("Spring Boot")).thenReturn("Spring Boot");
        when(candidateSkillRepository.existsByCandidateIdAndSkillNameIgnoreCaseAndSkillTypeAndIdNot(
                1L, "Spring Boot", SkillType.HARD, 10L)).thenReturn(false);

        doAnswer(invocation -> {
            var req = invocation.getArgument(0, UpdateCandidateSkillRequest.class);
            var existingSkill = invocation.getArgument(1, CandidateSkill.class);

            existingSkill.setSkillName(req.skillName());
            existingSkill.setSkillType(req.skillType());
            existingSkill.setLevel(req.level());

            return null;
        }).when(candidateSkillMapper).updateEntity(request, skill);

        when(candidateSkillRepository.save(skill)).thenReturn(skill);
        when(candidateSkillMapper.toResponse(skill)).thenReturn(response);

        var result = candidateSkillService.updateSkill(1L, 10L, request);

        assertThat(result.skillName()).isEqualTo("Spring Boot");
        assertThat(skill.getSkillName()).isEqualTo("Spring Boot");

        verify(candidateSkillRepository).save(skill);
    }

    @Test
    void deleteSkill_shouldDeleteSkillSuccessfully() {
        var candidate = Candidate.builder().id(1L).build();

        var skill = CandidateSkill.builder().id(10L).build();

        when(candidateRepository.findById(1L)).thenReturn(Optional.of(candidate));
        when(candidateSkillRepository.findByIdAndCandidateId(10L, 1L)).thenReturn(Optional.of(skill));

        candidateSkillService.deleteSkill(1L, 10L);

        verify(candidateSkillRepository).delete(skill);
    }
}
