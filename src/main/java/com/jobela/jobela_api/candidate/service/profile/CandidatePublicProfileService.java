package com.jobela.jobela_api.candidate.service.profile;

import com.jobela.jobela_api.candidate.dto.response.profile.CandidatePublicProfileResponse;

import java.util.List;

public interface CandidatePublicProfileService {
    CandidatePublicProfileResponse getCandidatePublicProfile(Long candidateId);
    List<CandidatePublicProfileResponse> getAllCandidatePublicProfiles();
}
