package com.jobela.jobela_api.candidate.dto.response.document;

import com.jobela.jobela_api.common.enums.DocumentType;

import java.time.LocalDateTime;


public record CandidateDocumentResponse(
        Long id,
        Long candidateId,
        DocumentType documentType,
        String fileName,
        String fileUrl,
        String mimeType,
        Long fileSize,
        LocalDateTime uploadedAt
) {
}
