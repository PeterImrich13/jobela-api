package com.jobela.jobela_api.candidate.dto.request.document;

import com.jobela.jobela_api.common.enums.DocumentType;
import jakarta.validation.constraints.Size;

public record UpdateCandidateDocumentRequest(
        DocumentType documentType,

        @Size(max = 255, message = "File name cannot be longer than 255 characters")
        String fileName,

        @Size(max = 1000, message = "File url cannot be longer than 1000 characters")
        String fileUrl,

        @Size(max = 100, message = "Mime type cannot be longer then 100 characters")
        String mimeType,

        Long fileSize
) {

}
