package com.jobela.jobela_api.candidate.dto.response.profile;

import com.jobela.jobela_api.common.enums.CandidateTargetPosition;

public record CandidatePublicProfileSummaryResponse(
        Long candidateId,

        String firstName,

        String lastName,

        String city,

        String country,

        String headline,

        CandidateTargetPosition targetPosition,

        Boolean openToWork,

        String profilePhotoUrl
) {
}
