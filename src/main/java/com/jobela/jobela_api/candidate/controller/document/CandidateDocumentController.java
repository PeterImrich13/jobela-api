package com.jobela.jobela_api.candidate.controller.document;


import com.jobela.jobela_api.candidate.dto.request.document.CreateCandidateDocumentRequest;
import com.jobela.jobela_api.candidate.dto.request.document.UpdateCandidateDocumentRequest;
import com.jobela.jobela_api.candidate.dto.response.document.CandidateDocumentResponse;
import com.jobela.jobela_api.candidate.service.document.CandidateDocumentService;
import com.jobela.jobela_api.common.enums.DocumentType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/candidates/{candidateId}/documents")
@RequiredArgsConstructor
public class CandidateDocumentController {

    private final CandidateDocumentService candidateDocumentService;

    @PostMapping
    public ResponseEntity<CandidateDocumentResponse> createCandidateDocument(
            @PathVariable Long candidateId, @Valid @RequestBody CreateCandidateDocumentRequest request
            ) {
        var response = candidateDocumentService.createCandidateDocument(candidateId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{candidateDocumentId}")
    public ResponseEntity<CandidateDocumentResponse> getCandidateDocument(
            @PathVariable Long candidateId, @PathVariable Long candidateDocumentId
    ) {
        var response = candidateDocumentService.getCandidateDocument(candidateId, candidateDocumentId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CandidateDocumentResponse>> getAllDocuments(
            @PathVariable Long candidateId, @RequestParam(name = "type", required = false) DocumentType documentType) {
        var response = candidateDocumentService.getCandidateDocuments(candidateId, documentType);

        return ResponseEntity.ok(response);
    }



    @PatchMapping("/{candidateDocumentId}")
    public ResponseEntity<CandidateDocumentResponse> updateCandidateDocument(
            @PathVariable Long candidateId, @PathVariable Long candidateDocumentId,
            @Valid @RequestBody UpdateCandidateDocumentRequest request
            ) {
        var response = candidateDocumentService.updateCandidateDocument(candidateId, candidateDocumentId, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{candidateDocumentId}")
    public ResponseEntity<Void> deleteDocument(
            @PathVariable Long candidateId, @PathVariable Long candidateDocumentId) {
         candidateDocumentService.deleteCandidateDocument(candidateId, candidateDocumentId);

         return ResponseEntity.noContent().build();
    }
}
