package com.jobela.jobela_api.candidate.service.language;

import com.jobela.jobela_api.candidate.dto.request.language.CreateCandidateLanguageRequest;
import com.jobela.jobela_api.candidate.dto.request.language.UpdateCandidateLanguageRequest;
import com.jobela.jobela_api.candidate.dto.response.language.CandidateLanguageResponse;

import java.util.List;

public interface CandidateLanguageService {
    CandidateLanguageResponse createCandidateLanguage(Long candidateId, CreateCandidateLanguageRequest request);
    CandidateLanguageResponse getLanguageById(Long candidateId, Long candidateLanguageId);
    List<CandidateLanguageResponse> getAllLanguagesByCandidateId(Long candidateId);
    CandidateLanguageResponse updateCandidateLanguage(Long candidateId, Long candidateLanguageId, UpdateCandidateLanguageRequest request);
    void deleteLanguage(Long candidateId, Long candidateLanguageId);
}
