package com.jobela.jobela_api.candidate.dto.request.education;

import com.jobela.jobela_api.common.enums.EducationLevel;
import com.jobela.jobela_api.common.enums.EducationType;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateCandidateEducationRequest(

        @Size(max = 150, message = "School name cannot be longer than 150 characters")
        String schoolName,

        EducationLevel educationLevel,

        EducationType educationType,

        @Size(max = 150, message = "Field of study cannot be longer than 150 characters")
        String fieldOfStudy,

        @Size(max = 150, message = "Degree cannot be longer than 150 characters")
        String degree,

        @Size(max = 100, message = "City cannot be longer than 100 characters")
        String city,

        @Size(max = 100, message = "Country cannot be longer than 100 characters")
        String country,

        LocalDate startDate,

        LocalDate endDate,

        Boolean currentlyStudying,

        @Size(max = 3000, message = "Description cannot be longer than 3000 characters")
        String description
) {
}
