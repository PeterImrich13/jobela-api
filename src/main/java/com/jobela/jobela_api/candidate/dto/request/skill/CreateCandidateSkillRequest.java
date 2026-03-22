package com.jobela.jobela_api.candidate.dto.request.skill;

import com.jobela.jobela_api.common.enums.SkillLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCandidateSkillRequest(

        @NotBlank(message = "Skill name cannot be blank")
        @Size(min = 1, max = 100, message = "Skill name must be between 1 and 100 characters")
        String skillName,

        @NotNull(message = "Skill level cannot be null")
        SkillLevel level
) {
}
