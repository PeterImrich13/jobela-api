package com.jobela.jobela_api.candidate.dto.request.workAuthorization;

import com.jobela.jobela_api.common.enums.AuthorizationType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateCandidateWorkAuthorizationRequest(
        @Size(max = 100, message = "Country cannot be longer than 100 characters")
        String country,

        AuthorizationType authorizationType,

        @FutureOrPresent(message = "Valid until must be int he future or present")
        LocalDate validUntil,

        Boolean sponsorshipRequired
) {
}
