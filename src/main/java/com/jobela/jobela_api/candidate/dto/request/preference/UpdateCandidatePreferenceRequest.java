package com.jobela.jobela_api.candidate.dto.request.preference;

import com.jobela.jobela_api.common.enums.CandidateTargetPosition;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateCandidatePreferenceRequest(
        CandidateTargetPosition targetPosition,

        Integer desiredSalaryMin,

        Integer desiredSalaryMax,

        @Size(max = 10, message = "Salary currency cannot be longer than 10 characters")
        String salaryCurrency,

        Boolean openToWork,

        LocalDate availabilityDate,

        Boolean relocationPreference
) {
}
