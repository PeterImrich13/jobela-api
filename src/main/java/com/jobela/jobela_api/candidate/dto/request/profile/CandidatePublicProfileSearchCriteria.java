package com.jobela.jobela_api.candidate.dto.request.profile;

import com.jobela.jobela_api.common.enums.AuthorizationType;
import com.jobela.jobela_api.common.enums.CandidateTargetPosition;
import com.jobela.jobela_api.common.enums.LanguageLevel;

public record CandidatePublicProfileSearchCriteria(
        String country,
        String city,
        String search,
        String skill,
        String language,
        Boolean openToWork,
        CandidateTargetPosition targetPosition,
        LanguageLevel level,
        AuthorizationType authorizationType,
        Boolean sponsorshipRequired
) {
}
