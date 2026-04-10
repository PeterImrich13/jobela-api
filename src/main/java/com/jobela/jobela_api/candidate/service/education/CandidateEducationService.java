package com.jobela.jobela_api.candidate.service.education;

import com.jobela.jobela_api.candidate.dto.request.education.CreateCandidateEducationRequest;
import com.jobela.jobela_api.candidate.dto.request.education.UpdateCandidateEducationRequest;
import com.jobela.jobela_api.candidate.dto.response.education.CandidateEducationResponse;

import java.util.*;

public interface CandidateEducationService {
    CandidateEducationResponse createCandidateEducation(Long candidateId, CreateCandidateEducationRequest request);
    CandidateEducationResponse getCandidateEducationByCandidateId(Long candidateId, Long candidateEducationId);
    List<CandidateEducationResponse> getAllEducationsByCandidateId(Long candidateId);
    CandidateEducationResponse updateCandidateEducation(Long candidateId, Long candidateEducationId, UpdateCandidateEducationRequest request);
    void deleteCandidateEducation(Long candidateId, Long candidateEducationId);
}
