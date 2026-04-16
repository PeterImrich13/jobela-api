package com.jobela.jobela_api.candidate.dto.request.workexperience;

import com.jobela.jobela_api.common.enums.EmploymentType;

import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateCandidateWorkExperienceRequest(
        @Size(max = 150, message = "Company name cannot be longer than 150 characters")
        String companyName,

        @Size(max = 150, message = "Job title cannot be longer than 150 characters")
        String jobTitle,

        EmploymentType employmentType,

        @Size(max = 100, message = "City cannot be longer than 100 characters")
        String city,

        @Size(max = 100, message = "Country cannot be longer than 100 characters")
        String country,

        @PastOrPresent
        LocalDate startDate,

        LocalDate endDate,

        Boolean currentlyWorking,

        @Size(max = 4000, message = "Description cannot be longer than 4000 characters")
        String description
) {
}
