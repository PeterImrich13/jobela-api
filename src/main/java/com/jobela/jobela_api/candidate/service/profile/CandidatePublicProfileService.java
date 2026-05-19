package com.jobela.jobela_api.candidate.service.profile;

import com.jobela.jobela_api.candidate.dto.request.profile.CandidatePublicProfileSearchCriteria;
import com.jobela.jobela_api.candidate.dto.response.profile.CandidatePublicProfileResponse;
import com.jobela.jobela_api.candidate.dto.response.profile.CandidatePublicProfileSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CandidatePublicProfileService {
    CandidatePublicProfileResponse getCandidatePublicProfile(Long candidateId);
    Page<CandidatePublicProfileSummaryResponse> getAllCandidatePublicProfiles(
            CandidatePublicProfileSearchCriteria criteria, Pageable pageable);
}
