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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CandidateSkillServiceImpl implements CandidateSkillService {

    private final CandidateSkillRepository candidateSkillRepository;
    private final CandidateSkillMapper candidateSkillMapper;
    private final CandidateRepository candidateRepository;

    @Override
    public CandidateSkillResponse createSkill(Long candidateId, CreateCandidateSkillRequest request) {

        log.info("Creating candidate skill for candidateId: {}", candidateId);

        var candidate = getCandidateByIdOrThrow(candidateId);

        if (candidateSkillRepository.existsByCandidateIdAndSkillNameIgnoreCase(candidateId, request.skillName())) {
            throw new CandidateSkillAlreadyExistsException("Skill with name" + request.skillName() +
                    " already exists for candidate with id: " + candidateId);
        }

        var skill = candidateSkillMapper.toEntity(request);
        skill.setCandidate(candidate);

        var savedSkill = candidateSkillRepository.save(skill);

        return candidateSkillMapper.toResponse(savedSkill);
    }

    @Override
    public CandidateSkillResponse getSkillById(Long candidateId, Long skillId) {

        log.info("Fetching skill with id: {} for candidateId: {}", skillId, candidateId);


        getCandidateByIdOrThrow(candidateId);

        var skill = getSkillByIdAndCandidateIdOrThrow(skillId, candidateId);
        return candidateSkillMapper.toResponse(skill);
    }

    @Override
    public List<CandidateSkillResponse> getAllSkillsByCandidateId(Long candidateId) {

        log.info("Fetching all skills for candidateId: {}", candidateId);

        getCandidateByIdOrThrow(candidateId);
        var skills = candidateSkillRepository.findAllByCandidateId(candidateId);

        return skills.stream()
                .map(candidateSkillMapper::toResponse)
                .toList();
    }

    @Override
    public CandidateSkillResponse updateSkill(Long candidateId, Long skillId, UpdateCandidateSkillRequest request) {

        log.info("Updating skill with id: {} for candidateId: {}", skillId, candidateId);

        getCandidateByIdOrThrow(candidateId);
         var skill = getSkillByIdAndCandidateIdOrThrow(skillId, candidateId);

         if (request.skillName() != null &&
                 candidateSkillRepository.existsByCandidateIdAndSkillNameIgnoreCaseAndIdNot(
                         candidateId, request.skillName(), skillId)) {
             throw new CandidateSkillAlreadyExistsException("Skill with name " + request.skillName() +
                     " already exists with name: " + request.skillName() + " for candidateId: " + candidateId);
         }

         candidateSkillMapper.updateEntity(request, skill);

         var updatedSkill = candidateSkillRepository.save(skill);
         return candidateSkillMapper.toResponse(updatedSkill);
    }

    @Override
    public void deleteSkill(Long candidateId, Long skillId) {

        log.info("Deleting skill with id: {} for candidateId {}", skillId, candidateId);

        var skill = getSkillByIdAndCandidateIdOrThrow(skillId, candidateId);

        candidateSkillRepository.delete(skill);

        log.info("Skill with id: {} for candidateId: {} successfully deleted", skillId, candidateId);
    }

    private Candidate getCandidateByIdOrThrow(Long candidateId) {
        return candidateRepository.findById(candidateId)
                .orElseThrow(() -> new CandidateNotFoundException("Candidate not found with id: " + candidateId));
    }

    private CandidateSkill getSkillByIdAndCandidateIdOrThrow(Long skillId, Long candidateId) {
        return candidateSkillRepository.findByIdAndCandidateId(skillId, candidateId)
                .orElseThrow(() -> new SkillNotFoundException("Skill not found with id: " + skillId + " for candidate with id: " + candidateId));
    }
}
