package com.jobela.jobela_api.candidate.service.skill;

import com.jobela.jobela_api.candidate.dto.request.skill.CreateCandidateSkillRequest;
import com.jobela.jobela_api.candidate.dto.request.skill.UpdateCandidateSkillRequest;
import com.jobela.jobela_api.candidate.dto.response.skill.CandidateSkillResponse;
import com.jobela.jobela_api.candidate.entity.Candidate;
import com.jobela.jobela_api.candidate.entity.CandidateSkill;
import com.jobela.jobela_api.candidate.mapper.CandidateSkillMapper;
import com.jobela.jobela_api.candidate.repository.CandidateRepository;
import com.jobela.jobela_api.candidate.repository.CandidateSkillRepository;
import com.jobela.jobela_api.common.enums.SkillType;
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

        validateSkillName(cleanedSkillName);
        validateSkillType(request.skillType());


        if (candidateSkillRepository.existsByCandidateIdAndSkillNameIgnoreCaseAndSkillType(
                candidateId, cleanedSkillName, request.skillType()
        )) {
            throw new CandidateSkillAlreadyExistsException("Skill with name " + cleanedSkillName +
                    " and type " + request.skillType() + " already exists for candidate with id: " + candidateId);
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
    @Transactional(readOnly = true)
    public List<CandidateSkillResponse> getAllSkillsByCandidateIdAndType(
            Long candidateId, SkillType skillType
    ) {
        log.info("Fetching skills for candidateId={} and skillType={}", candidateId, skillType);

        getCandidateByIdOrThrow(candidateId);
        validateSkillType(skillType);

        var skills = candidateSkillRepository.findAllByCandidateIdAndSkillType(candidateId, skillType);

        return skills.stream()
                .map(candidateSkillMapper::toResponse)
                .toList();
    }

    @Override
    public CandidateSkillResponse updateSkill(Long candidateId, Long skillId, UpdateCandidateSkillRequest request) {

        log.info("Updating skill with id={} for candidateId={}", skillId, candidateId);

        getCandidateByIdOrThrow(candidateId);
         var skill = getSkillByIdAndCandidateIdOrThrow(skillId, candidateId);

         var finalSkillName = resolveFinalSkillName(request, skill);
         var finalSkillType = resolveFinalSkillType(request, skill);

         if (candidateSkillRepository.existsByCandidateIdAndSkillNameIgnoreCaseAndSkillTypeAndIdNot(
                     candidateId, finalSkillName, finalSkillType, skillId)) {
                 throw new CandidateSkillAlreadyExistsException("Skill with name " + finalSkillName +
                         " and type " + finalSkillType + " already exists for candidateId: " + candidateId);
             }

         candidateSkillMapper.updateEntity(request, skill);

         validateRequiredFields(skill);

         var updatedSkill = candidateSkillRepository.save(skill);

         log.info("Candidate skill updated successfully with id={} for candidateId={}", updatedSkill.getId(), candidateId);

         return candidateSkillMapper.toResponse(updatedSkill);
    }

    @Override
    public void deleteSkill(Long candidateId, Long skillId) {

        log.info("Deleting skill with id={} for candidateId={}", skillId, candidateId);

        getCandidateByIdOrThrow(candidateId);
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
        validateSkillName(skill.getSkillName());
        validateSkillType(skill.getSkillType());

        if (skill.getLevel() == null) {
            throw new BadRequestException("Skill level cannot be null");
        }
    }

    private void validateSkillName(String skillName) {
        if (skillName == null || skillName.isBlank()) {
            throw new BadRequestException("Skill name cannot be blank");
        }
    }

    private void validateSkillType(SkillType skillType) {
        if (skillType == null) {
            throw new BadRequestException("Skill type cannot be null");
        }
    }

    private String resolveFinalSkillName(UpdateCandidateSkillRequest request, CandidateSkill candidateSkill) {

        if (request.skillName() == null) {
            return candidateSkill.getSkillName();
        }

        var cleanedSkillName = stringMapperHelper.clean(request.skillName());
        validateSkillName(cleanedSkillName);
        return cleanedSkillName;
    }

    private SkillType resolveFinalSkillType(UpdateCandidateSkillRequest request, CandidateSkill candidateSkill) {
        var finalSkillType = request.skillType() != null ? request.skillType() : candidateSkill.getSkillType();
        validateSkillType(finalSkillType);
        return finalSkillType;
    }
}
