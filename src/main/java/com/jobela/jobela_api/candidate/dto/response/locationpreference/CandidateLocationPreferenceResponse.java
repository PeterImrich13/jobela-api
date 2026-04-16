package com.jobela.jobela_api.candidate.dto.response.locationpreference;


import java.time.LocalDateTime;

public record CandidateLocationPreferenceResponse(

        Long id,

        Long candidateId,

        String city,

        String country,

        String region,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}
