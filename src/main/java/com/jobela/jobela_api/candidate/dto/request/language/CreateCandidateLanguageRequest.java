package com.jobela.jobela_api.candidate.dto.request.language;

import com.jobela.jobela_api.common.enums.LanguageLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCandidateLanguageRequest(

        @NotBlank(message = "Language name cannot be blank")
        @Size(max = 100, message = "Language name cannot be longer than 100 characters")
        String languageName,

        @NotNull(message = "Level cannot be null")
        LanguageLevel level
) {
}
