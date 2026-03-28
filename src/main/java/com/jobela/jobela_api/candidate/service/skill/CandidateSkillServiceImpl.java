package com.jobela.jobela_api.candidate.service.skill;

import com.jobela.jobela_api.candidate.dto.request.skill.CreateCandidateSkillRequest;
import com.jobela.jobela_api.candidate.dto.request.skill.UpdateCandidateSkillRequest;
import com.jobela.jobela_api.candidate.dto.response.skill.CandidateSkillResponse;
import com.jobela.jobela_api.candidate.entity.Candidate;
import com.jobela.jobela_api.candidate.entity.CandidateSkill;
import com.jobela.jobela_api.candidate.mapper.CandidateSkillMapper;
import com.jobela.jobela_api.candidate.repository.CandidateRepository;
import com.jobela.jobela_api.candidate.repository.CandidateSkillRepository;
import com.jobela.jobela_api.common.exception.CandidateNotFoundException;
import com.jobela.jobela_api.common.exception.CandidateSkillAlreadyExistsException;
import com.jobela.jobela_api.common.exception.SkillNotFoundException;
import com.jobela.jobela_api.common.exception.BadRequestException;
import com.jobela.jobela_api.common.mapper.StringMapperHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CandidateSkillServiceImpl implements CandidateSkillService {

    private final CandidateSkillRepository candidateSkillRepository;
    private final CandidateSkillMapper candidateSkillMapper;
    private final CandidateRepository candidateRepository;
    private final StringMapperHelper stringMapperHelper;

    @Override
    public CandidateSkillResponse createSkill(Long candidateId, CreateCandidateSkillRequest request) {

        log.info("Creating candidate skill for candidateId={}", candidateId);

        var candidate = getCandidateByIdOrThrow(candidateId);
        var cleanedSkillName = stringMapperHelper.clean(request.skillName());

        if (cleanedSkillName == null || cleanedSkillName.isEmpty()) {
            throw new BadRequestException("Skill name cannot be blank");
        }

        if (candidateSkillRepository.existsByCandidateIdAndSkillNameIgnoreCase(candidateId, cleanedSkillName)) {
            throw new CandidateSkillAlreadyExistsException("Skill with name " + cleanedSkillName +
                    " already exists for candidate with id: " + candidateId);
        }

        var skill = candidateSkillMapper.toEntity(request);
        skill.setCandidate(candidate);

        validateRequiredFields(skill);

        var savedSkill = candidateSkillRepository.save(skill);

        log.info("Candidate skill created successfully with id={} for candidateId={}", savedSkill.getId(), candidateId);

        return candidateSkillMapper.toResponse(savedSkill);
    }

    @Override
    @Transactional(readOnly = true)
    public CandidateSkillResponse getSkillById(Long candidateId, Long skillId) {

        log.info("Fetching skill with id={} for candidateId={}", skillId, candidateId);


        getCandidateByIdOrThrow(candidateId);

        var skill = getSkillByIdAndCandidateIdOrThrow(skillId, candidateId);
        return candidateSkillMapper.toResponse(skill);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CandidateSkillResponse> getAllSkillsByCandidateId(Long candidateId) {

        log.info("Fetching all skills for candidateId={}", candidateId);

        getCandidateByIdOrThrow(candidateId);
        var skills = candidateSkillRepository.findAllByCandidateId(candidateId);

        return skills.stream()
                .map(candidateSkillMapper::toResponse)
                .toList();
    }

    @Override
    public CandidateSkillResponse updateSkill(Long candidateId, Long skillId, UpdateCandidateSkillRequest request) {

        log.info("Updating skill with id={} for candidateId={}", skillId, candidateId);

        getCandidateByIdOrThrow(candidateId);
         var skill = getSkillByIdAndCandidateIdOrThrow(skillId, candidateId);

         String cleanedSkillName = null;
         if (request.skillName() != null) {
             cleanedSkillName = stringMapperHelper.clean(request.skillName());

             if (cleanedSkillName == null || cleanedSkillName.isEmpty()) {
                 throw new BadRequestException("Skill cannot be blank");
             }
             if (candidateSkillRepository.existsByCandidateIdAndSkillNameIgnoreCaseAndIdNot(
                     candidateId, cleanedSkillName, skillId)) {
                 throw new CandidateSkillAlreadyExistsException("Skill with name " + cleanedSkillName +
                         " already exists for candidateId: " + candidateId);
             }
         }

         candidateSkillMapper.updateEntity(request, skill);

         validateUpdatedFields(request, skill);

         var updatedSkill = candidateSkillRepository.save(skill);

         log.info("Candidate skill updated successfully with id={} for candidateId={}", updatedSkill.getId(), candidateId);

         return candidateSkillMapper.toResponse(updatedSkill);
    }

    @Override
    public void deleteSkill(Long candidateId, Long skillId) {

        log.info("Deleting skill with id={} for candidateId={}", skillId, candidateId);

        var skill = getSkillByIdAndCandidateIdOrThrow(skillId, candidateId);

        candidateSkillRepository.delete(skill);

        log.info("Skill with id={} for candidateId={} successfully deleted", skillId, candidateId);
    }

    private Candidate getCandidateByIdOrThrow(Long candidateId) {
        return candidateRepository.findById(candidateId)
                .orElseThrow(() -> new CandidateNotFoundException("Candidate not found with id: " + candidateId));
    }

    private CandidateSkill getSkillByIdAndCandidateIdOrThrow(Long skillId, Long candidateId) {
        return candidateSkillRepository.findByIdAndCandidateId(skillId, candidateId)
                .orElseThrow(() -> new SkillNotFoundException("Skill not found with id: " + skillId + " for candidate with id: " + candidateId));
    }

    private void validateRequiredFields(CandidateSkill skill) {
        if (skill.getSkillName() == null || skill.getSkillName().isBlank()) {
            throw new BadRequestException("Skill name cannot be blank");
        }
    }

    private void validateUpdatedFields(UpdateCandidateSkillRequest request, CandidateSkill skill) {
        if (request.skillName() != null && (skill.getSkillName() == null || skill.getSkillName().isBlank())) {
            throw new BadRequestException("Skill name cannot be blank");
        }
    }
}
