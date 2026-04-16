package com.jobela.jobela_api.candidate.service.workexperience;

import com.jobela.jobela_api.candidate.dto.request.workexperience.CreateCandidateWorkExperienceRequest;
import com.jobela.jobela_api.candidate.dto.request.workexperience.UpdateCandidateWorkExperienceRequest;
import com.jobela.jobela_api.candidate.dto.response.workexperience.CandidateWorkExperienceResponse;

import java.util.List;

public interface CandidateWorkExperienceService {
    CandidateWorkExperienceResponse createWorkExperience(
            Long candidateId, CreateCandidateWorkExperienceRequest request);
    CandidateWorkExperienceResponse getWorkExperience(Long candidateId, Long candidateWorkExperienceId);
    List<CandidateWorkExperienceResponse> getAllWorkExperiences(Long candidateId);
    CandidateWorkExperienceResponse updateWorkExperience(
            Long candidateId, Long candidateWorkExperienceId, UpdateCandidateWorkExperienceRequest request);
    void deleteWorkExperience(Long candidateId, Long candidateWorkExperienceId);
}
