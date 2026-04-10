package com.jobela.jobela_api.candidate.dto.response.certification;



import java.time.LocalDate;

public record CandidateCertificationResponse(

        Long id,
        Long candidateId,
        String name,
        String issuer,
        LocalDate issueDate,
        String credentialUrl
) {
}
