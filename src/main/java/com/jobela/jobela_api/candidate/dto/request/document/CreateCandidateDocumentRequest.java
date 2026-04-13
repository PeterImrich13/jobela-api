package com.jobela.jobela_api.candidate.dto.request.document;

import com.jobela.jobela_api.common.enums.DocumentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCandidateDocumentRequest(
        @NotNull(message = "Document type cannot be null")
        DocumentType documentType,

        @NotBlank(message = "File name cannot be blank")
        @Size(max = 255, message = "File name cannot be longer than 255 characters")
        String fileName,

        @NotBlank(message = "File url cannot be blank")
        @Size(max = 1000, message = "File url cannot be longer than 1000 characters")
        String fileUrl,

        @Size(max = 100, message = "Mime type cannot be longer then 100 characters")
        String mimeType,

        Long fileSize
) {
}
