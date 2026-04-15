package com.jobela.jobela_api.candidate.dto.response.workAuthorization;

import com.jobela.jobela_api.common.enums.AuthorizationType;


import java.time.LocalDate;
import java.time.LocalDateTime;

public record CandidateWorkAuthorizationResponse(
        Long id,
        Long candidateId,        
        String country,
        AuthorizationType authorizationType,
        LocalDate validUntil,
        Boolean sponsorshipRequired,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
