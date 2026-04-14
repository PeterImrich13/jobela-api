package com.jobela.jobela_api.candidate.service.locationPreference;

import com.jobela.jobela_api.candidate.dto.request.locationPreference.CreateCandidateLocationPreferenceRequest;
import com.jobela.jobela_api.candidate.dto.request.locationPreference.UpdateCandidateLocationPreferenceRequest;
import com.jobela.jobela_api.candidate.dto.response.locationPreference.CandidateLocationPreferenceResponse;

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
