package com.jobela.jobela_api.candidate.service.document;

import com.jobela.jobela_api.candidate.dto.request.document.CreateCandidateDocumentRequest;
import com.jobela.jobela_api.candidate.dto.request.document.UpdateCandidateDocumentRequest;
import com.jobela.jobela_api.candidate.dto.response.document.CandidateDocumentResponse;
import com.jobela.jobela_api.common.enums.DocumentType;

import java.util.List;

public interface CandidateDocumentService {
    CandidateDocumentResponse createCandidateDocument(Long candidateId, CreateCandidateDocumentRequest request);

    CandidateDocumentResponse getCandidateDocument(Long candidateId, Long candidateDocumentId);

    List<CandidateDocumentResponse> getCandidateDocuments(Long candidateId, DocumentType documentType);

    CandidateDocumentResponse updateCandidateDocument(
            Long candidateId, Long candidateDocumentId, UpdateCandidateDocumentRequest request);

    void deleteCandidateDocument(Long candidateId, Long candidateDocumentId);
}