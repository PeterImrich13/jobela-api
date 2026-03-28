package com.jobela.jobela_api.candidate.dto.response.language;

import com.jobela.jobela_api.common.enums.LanguageLevel;

import java.time.LocalDateTime;

public record CandidateLanguageResponse(
        Long id,
        Long candidateId,
        String languageName,
        LanguageLevel level,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
