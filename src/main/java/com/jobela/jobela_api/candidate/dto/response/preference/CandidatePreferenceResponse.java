package com.jobela.jobela_api.candidate.dto.response.preference;

import com.jobela.jobela_api.common.enums.CandidateTargetPosition;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CandidatePreferenceResponse(
        Long id,

        Long candidateId,

        CandidateTargetPosition targetPosition,

        Integer desiredSalaryMin,

        Integer desiredSalaryMax,

        String salaryCurrency,

        Boolean openToWork,

        LocalDate availabilityDate,

        Boolean relocationPreference,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}
