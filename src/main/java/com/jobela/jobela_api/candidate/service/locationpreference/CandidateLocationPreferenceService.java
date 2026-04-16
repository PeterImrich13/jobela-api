package com.jobela.jobela_api.candidate.service.locationpreference;

import com.jobela.jobela_api.candidate.dto.request.locationpreference.CreateCandidateLocationPreferenceRequest;
import com.jobela.jobela_api.candidate.dto.request.locationpreference.UpdateCandidateLocationPreferenceRequest;
import com.jobela.jobela_api.candidate.dto.response.locationpreference.CandidateLocationPreferenceResponse;

import java.util.List;

public interface CandidateLocationPreferenceService {
    CandidateLocationPreferenceResponse createLocationPreference(
            Long candidateId, CreateCandidateLocationPreferenceRequest request);
    CandidateLocationPreferenceResponse getLocationPreference(Long candidateId, Long candidateLocationPreferenceId);
    List<CandidateLocationPreferenceResponse> getAllLocationPreferences(Long candidateId);
    CandidateLocationPreferenceResponse updateLocationPreference(
            Long candidateId, Long candidateLocationPreferenceId, UpdateCandidateLocationPreferenceRequest request);
    void deleteLocationPreference(Long candidateId, Long candidateLocationPreferenceId);
}
