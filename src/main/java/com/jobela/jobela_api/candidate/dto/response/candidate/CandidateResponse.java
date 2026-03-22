package com.jobela.jobela_api.candidate.dto.response.candidate;

import com.jobela.jobela_api.common.enums.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CandidateResponse(
        Long id,
        Long userId,
        String titleBefore,
        String titleAfter,
        String firstName,
        String lastName,
        Gender gender,
        String phone,
        String city,
        String country,
        String nationality,
        LocalDate dateOfBirth,
        String headline,
        String summary,
        String profilePhotoUrl,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
