package com.jobela.jobela_api.candidate.dto.request.preference;

import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateCandidatePreferenceRequest(
        Integer desiredSalaryMin,

        Integer desiredSalaryMax,

        @Size(max = 10, message = "Salary currency cannot be longer than 10 characters")
        String salaryCurrency,

        Boolean openToWork,

        LocalDate availabilityDate,

        Boolean relocationPreference
) {
}
