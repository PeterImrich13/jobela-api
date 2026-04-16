package com.jobela.jobela_api.candidate.dto.request.locationpreference;

import jakarta.validation.constraints.Size;

public record CreateCandidateLocationPreferenceRequest(
        @Size(max = 100, message = "City cannot be longer than 100 characters")
        String city,

        @Size(max = 100, message = "Country cannot be longer than 100 characters")
        String country,

        @Size(max = 100, message = "Region cannot be longer than 100 characters")
        String region
        ) {
}
