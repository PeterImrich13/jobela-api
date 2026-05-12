package com.jobela.jobela_api.candidate.dto.request.profile;

public record CandidatePublicProfileSearchCriteria(
        String country,
        String city,
        String search
) {
}
