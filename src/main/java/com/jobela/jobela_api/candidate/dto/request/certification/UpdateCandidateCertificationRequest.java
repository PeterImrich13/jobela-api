package com.jobela.jobela_api.candidate.dto.request.certification;


import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateCandidateCertificationRequest(
        @Size(max = 150, message = "Name cannot be longer than 150 characters")
        String name,

        @Size(max = 150, message = "Issuer cannot be longer than 150 characters")
        String issuer,

        @PastOrPresent(message = "Issue date cannot be in the future")
        LocalDate issueDate,

        @Size(max = 255, message = "Credential url cannot be longer than 255 characters")
        String credentialUrl) {
}
