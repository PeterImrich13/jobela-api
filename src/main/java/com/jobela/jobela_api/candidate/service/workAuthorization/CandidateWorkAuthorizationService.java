package com.jobela.jobela_api.candidate.service.workAuthorization;

import com.jobela.jobela_api.candidate.dto.request.workAuthorization.CreateCandidateWorkAuthorizationRequest;
import com.jobela.jobela_api.candidate.dto.request.workAuthorization.UpdateCandidateWorkAuthorizationRequest;
import com.jobela.jobela_api.candidate.dto.response.workAuthorization.CandidateWorkAuthorizationResponse;

import java.util.List;

public interface CandidateWorkAuthorizationService {
    CandidateWorkAuthorizationResponse createWorkAuthorization(
            Long candidateId, CreateCandidateWorkAuthorizationRequest request);
    CandidateWorkAuthorizationResponse getWorkAuthorization(Long candidateId, Long candidateWorkAuthorizationId);
    List<CandidateWorkAuthorizationResponse> getAllWorkAuthorizations(Long candidateId);
    CandidateWorkAuthorizationResponse updateWorkAuthorization(
            Long candidateId, Long candidateWorkAuthorizationId, UpdateCandidateWorkAuthorizationRequest request);
    void deleteWorkAuthorization(Long candidateId, Long candidateWorkAuthorizationId);
}
