package com.jobela.jobela_api.candidate.service.skill;

import com.jobela.jobela_api.candidate.dto.request.skill.CreateCandidateSkillRequest;
import com.jobela.jobela_api.candidate.dto.request.skill.UpdateCandidateSkillRequest;
import com.jobela.jobela_api.candidate.dto.response.skill.CandidateSkillResponse;

import java.util.List;


public interface CandidateSkillService {
    public CandidateSkillResponse createSkill(Long candidateId, CreateCandidateSkillRequest request);
    public CandidateSkillResponse getSkillById(Long candidateId, Long skillId);
    public List<CandidateSkillResponse> getAllSkillsByCandidateId(Long candidateId);
    public CandidateSkillResponse updateSkill(Long candidateId, Long skillId, UpdateCandidateSkillRequest request);
    public void deleteSkill(Long candidateId, Long skillId);
}
