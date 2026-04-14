package com.jobela.jobela_api.candidate.dto.request.preference;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateCandidatePreferenceRequest(
        Integer desiredSalaryMin,

        Integer desiredSalaryMax,

        @Size(max = 10, message = "Salary currency cannot be longer than 10 characters")
        @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be 3 uppercase letters (e.g. CHF)")
        String salaryCurrency,

        @NotNull
        Boolean openToWork,

        LocalDate availabilityDate,

        @NotNull
        Boolean relocationPreference
) {
}
