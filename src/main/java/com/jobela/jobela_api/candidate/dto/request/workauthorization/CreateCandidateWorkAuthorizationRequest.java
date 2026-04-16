package com.jobela.jobela_api.candidate.dto.request.workauthorization;

import com.jobela.jobela_api.common.enums.AuthorizationType;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CreateCandidateWorkAuthorizationRequest(
        @NotBlank(message = "Country cannot be blank")
        @Size(max = 100, message = "Country cannot be longer than 100 characters")
        String country,

        @NotNull(message = "Authorization type cannot be null")
        AuthorizationType authorizationType,

        @FutureOrPresent(message = "Valid until must be int he future or present")
        LocalDate validUntil,

        @NotNull
        Boolean sponsorshipRequired
) {
}
