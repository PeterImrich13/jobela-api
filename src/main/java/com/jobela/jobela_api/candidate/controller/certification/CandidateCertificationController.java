package com.jobela.jobela_api.candidate.controller.certification;


import com.jobela.jobela_api.candidate.dto.request.certification.CreateCandidateCertificationRequest;
import com.jobela.jobela_api.candidate.dto.request.certification.UpdateCandidateCertificationRequest;
import com.jobela.jobela_api.candidate.dto.response.certification.CandidateCertificationResponse;
import com.jobela.jobela_api.candidate.service.certification.CandidateCertificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/candidates/{candidateId}/certifications")
@PreAuthorize("hasRole('ADMIN') or (hasRole('CANDIDATE') and @candidateSecurity.isOwner(#candidateId, authentication))")
public class CandidateCertificationController {

    private final CandidateCertificationService candidateCertificationService;

    @PostMapping
    public ResponseEntity<CandidateCertificationResponse> createCandidateCertification(
            @PathVariable Long candidateId, @Valid @RequestBody CreateCandidateCertificationRequest request
    ) {
        var response = candidateCertificationService.createCandidateCertification(candidateId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{candidateCertificationId}")
    public ResponseEntity<CandidateCertificationResponse> getCandidateCertification(
            @PathVariable Long candidateId, @PathVariable Long candidateCertificationId
    ) {
        var response = candidateCertificationService.getCandidateCertificationById(
                candidateId, candidateCertificationId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CandidateCertificationResponse>> getAllCandidateCertifications(
            @PathVariable Long candidateId) {

        var response = candidateCertificationService.getAllCandidateCertifications(candidateId);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{candidateCertificationId}")
    public ResponseEntity<CandidateCertificationResponse> updateCandidateCertification(
            @PathVariable Long candidateId, @PathVariable Long candidateCertificationId,
            @Valid @RequestBody UpdateCandidateCertificationRequest request
            ) {
        var response = candidateCertificationService.updateCandidateCertification(
                candidateId, candidateCertificationId, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{candidateCertificationId}")
    public ResponseEntity<Void> deleteCandidateCertification(
            @PathVariable Long candidateId, @PathVariable Long candidateCertificationId) {
        candidateCertificationService.deleteCandidateCertification(candidateId, candidateCertificationId);

        return ResponseEntity.noContent().build();
    }
}
