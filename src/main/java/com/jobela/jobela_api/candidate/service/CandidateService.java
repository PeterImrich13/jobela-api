package com.jobela.jobela_api.candidate.service;

import com.jobela.jobela_api.candidate.dto.request.candidate.UpdateCandidateRequest;
import com.jobela.jobela_api.candidate.dto.response.candidate.CandidateResponse;
import com.jobela.jobela_api.candidate.dto.request.candidate.CreateCandidateRequest;

public interface CandidateService {

    CandidateResponse createCandidate(Long userId, CreateCandidateRequest request);
    CandidateResponse getCandidateById(Long candidateId);
    CandidateResponse getCandidateByUserId(Long userId);
    CandidateResponse updateCandidate(Long candidateId, UpdateCandidateRequest request);
    void deleteCandidate(Long candidateId);
}
