package com.jobela.jobela_api.candidate.dto.request.candidate;

import com.jobela.jobela_api.common.enums.Gender;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateCandidateRequest(

        @Size(max = 50)
        String titleBefore,

        @Size(max = 50)
        String titleAfter,

        @Size(max = 100)
        String firstName,

        @Size(max = 100)
        String lastName,

        Gender gender,

        @Size(max = 50)
        String phone,

        @Size(max = 100)
        String city,

        @Size(max = 100)
        String country,

        @Size(max = 100)
        String nationality,

        @Past
        LocalDate dateOfBirth,

        @Size(max = 150)
        String headline,

        @Size(max = 3000)
        String summary,

        @Size(max = 500)
        String profilePhotoUrl
) {
}
