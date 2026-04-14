package com.jobela.jobela_api.candidate.service.preference;

import com.jobela.jobela_api.candidate.dto.request.preference.CreateCandidatePreferenceRequest;
import com.jobela.jobela_api.candidate.dto.request.preference.UpdateCandidatePreferenceRequest;
import com.jobela.jobela_api.candidate.dto.response.preference.CandidatePreferenceResponse;
import com.jobela.jobela_api.candidate.repository.CandidatePreferenceRepository;

public interface CandidatePreferenceService {
    CandidatePreferenceResponse createCandidatePreference(Long candidateId, CreateCandidatePreferenceRequest request);
    CandidatePreferenceResponse getCandidatePreference(Long candidateId, Long candidatePreferenceId);
    CandidatePreferenceResponse updateCandidatePreference(
            Long candidateId, Long candidatePreferenceId, UpdateCandidatePreferenceRequest request);
    void deleteCandidatePreference(Long candidateId, Long candidatePreferenceId);
}
