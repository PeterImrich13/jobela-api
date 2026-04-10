package com.jobela.jobela_api.candidate.dto.request.education;

import com.jobela.jobela_api.common.enums.EducationLevel;
import com.jobela.jobela_api.common.enums.EducationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateCandidateEducationRequest(

        @NotBlank(message = "School name cannot be blank")
        @Size(max = 150, message = "School name cannot be longer than 150 characters")
        String schoolName,

        @NotNull(message = "Education level cannot be null")
        EducationLevel educationLevel,

        @NotNull(message = "Education type cannot be null")
        EducationType educationType,

        @Size(max = 150, message = "Field of study cannot be longer than 150 characters")
        String fieldOfStudy,

        @NotBlank(message = "Degree cannot be blank")
        @Size(max = 150, message = "Degree cannot be longer than 150 characters")
        String degree,

        @Size(max = 100, message = "City cannot be longer than 100 characters")
        String city,

        @Size(max = 100, message = "Country cannot be longer than 100 characters")
        String country,

        @NotNull(message = "Start date cannot be null")
        LocalDate startDate,

        LocalDate endDate,

        @NotNull(message = "Currently studying cannot be null")
        Boolean currentlyStudying,

        @Size(max = 3000, message = "Description cannot be longer than 3000 characters")
        String description
) {
}
