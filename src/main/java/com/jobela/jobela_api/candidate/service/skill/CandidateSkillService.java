package com.jobela.jobela_api.candidate.service.skill;

import com.jobela.jobela_api.candidate.dto.request.skill.CreateCandidateSkillRequest;
import com.jobela.jobela_api.candidate.dto.request.skill.UpdateCandidateSkillRequest;
import com.jobela.jobela_api.candidate.dto.response.skill.CandidateSkillResponse;
import com.jobela.jobela_api.common.enums.SkillType;

import java.util.List;


public interface CandidateSkillService {
    CandidateSkillResponse createSkill(Long candidateId, CreateCandidateSkillRequest request);
    CandidateSkillResponse getSkillById(Long candidateId, Long skillId);
    List<CandidateSkillResponse> getAllSkillsByCandidateId(Long candidateId);
    List<CandidateSkillResponse> getAllSkillsByCandidateIdAndType(Long candidateId, SkillType skillType);
    CandidateSkillResponse updateSkill(Long candidateId, Long skillId, UpdateCandidateSkillRequest request);
    void deleteSkill(Long candidateId, Long skillId);
}
