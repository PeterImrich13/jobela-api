package com.jobela.jobela_api.candidate.dto.request.workexperience;

import com.jobela.jobela_api.common.enums.EmploymentType;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CreateCandidateWorkExperienceRequest(

        @NotBlank(message = "Company name cannot be blank")
        @Size(max = 150, message = "Company name cannot be longer than 150 characters")
        String companyName,

        @NotBlank(message = "Job title cannot be blank")
        @Size(max = 150, message = "Job title cannot be longer than 150 characters")
        String jobTitle,

        EmploymentType employmentType,

        @Size(max = 100, message = "City cannot be longer than 100 characters")
        String city,

        @Size(max = 100, message = "Country cannot be longer than 100 characters")
        String country,

        @NotNull(message = "Start date cannot be null")
        @PastOrPresent
        LocalDate startDate,

        LocalDate endDate,

        @NotNull(message = "Currently working cannot be null")
        Boolean currentlyWorking,

        @Size(max = 4000, message = "Description cannot be longer than 4000 characters")
        String description
        ) {
}
