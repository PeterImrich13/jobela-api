package com.jobela.jobela_api.candidate.dto.request.skill;

import com.jobela.jobela_api.common.enums.SkillLevel;
import com.jobela.jobela_api.common.enums.SkillType;
import jakarta.validation.constraints.Size;

public record UpdateCandidateSkillRequest(

        @Size(min = 1, max = 100, message = "Skill name must be between 1 and 100 characters")
        String skillName,

        SkillType skillType,

        SkillLevel level
) {
}
