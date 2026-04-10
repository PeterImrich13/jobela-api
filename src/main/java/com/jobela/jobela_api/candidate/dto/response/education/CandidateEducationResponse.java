package com.jobela.jobela_api.candidate.dto.response.education;

import com.jobela.jobela_api.common.enums.EducationLevel;
import com.jobela.jobela_api.common.enums.EducationType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CandidateEducationResponse(
        Long id,
        Long candidateId,
        String schoolName,
        EducationLevel educationLevel,
        EducationType educationType,
        String fieldOfStudy,
        String degree,
        String city,
        String country,
        LocalDate startDate,
        LocalDate endDate,
        Boolean currentlyStudying,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
