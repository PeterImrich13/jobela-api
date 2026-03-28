package com.jobela.jobela_api.candidate.dto.request.language;

import com.jobela.jobela_api.common.enums.LanguageLevel;
import jakarta.validation.constraints.Size;

public record UpdateCandidateLanguageRequest(

        @Size(max = 100, message = "Language name cannot be longer than 100 characters")
        String languageName,
        LanguageLevel level
) {
}
