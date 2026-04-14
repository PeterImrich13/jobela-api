package com.jobela.jobela_api.candidate.dto.response.preference;


import java.time.LocalDate;
import java.time.LocalDateTime;

public record CandidatePreferenceResponse(
        Long id,

        Long candidateId,

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
