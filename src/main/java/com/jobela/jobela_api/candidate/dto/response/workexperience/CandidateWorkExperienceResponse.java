package com.jobela.jobela_api.candidate.dto.response.workexperience;

import com.jobela.jobela_api.common.enums.EmploymentType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CandidateWorkExperienceResponse(

        Long id,

        Long candidateId,

        String companyName,


        String jobTitle,

        EmploymentType employmentType,

        String city,

        String country,

        LocalDate startDate,

        LocalDate endDate,

        Boolean currentlyWorking,

        String description,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}
