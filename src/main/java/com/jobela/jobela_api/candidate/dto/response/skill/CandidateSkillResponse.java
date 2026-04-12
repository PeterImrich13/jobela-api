package com.jobela.jobela_api.candidate.dto.response.skill;


import com.jobela.jobela_api.common.enums.SkillLevel;
import com.jobela.jobela_api.common.enums.SkillType;

import java.time.LocalDateTime;

public record CandidateSkillResponse(
        Long id,
        Long candidateId,
        String skillName,
        SkillType skillType,
        SkillLevel level,
        LocalDateTime createdAt

) {
}
