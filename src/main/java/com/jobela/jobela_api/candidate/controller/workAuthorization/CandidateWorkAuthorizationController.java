package com.jobela.jobela_api.candidate.controller.workAuthorization;


import com.jobela.jobela_api.candidate.dto.request.workAuthorization.CreateCandidateWorkAuthorizationRequest;
import com.jobela.jobela_api.candidate.dto.request.workAuthorization.UpdateCandidateWorkAuthorizationRequest;
import com.jobela.jobela_api.candidate.dto.response.workAuthorization.CandidateWorkAuthorizationResponse;
import com.jobela.jobela_api.candidate.service.workAuthorization.CandidateWorkAuthorizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/candidates/{candidateId}/work-authorizations")
@RequiredArgsConstructor
public class CandidateWorkAuthorizationController {

    private final CandidateWorkAuthorizationService candidateWorkAuthorizationService;

    @PostMapping
    public ResponseEntity<CandidateWorkAuthorizationResponse> createWorkAuthorization(
            @PathVariable Long candidateId, @Valid @RequestBody CreateCandidateWorkAuthorizationRequest request
            ) {
        var response = candidateWorkAuthorizationService.createWorkAuthorization(candidateId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{candidateWorkAuthorizationId}")
    public ResponseEntity<CandidateWorkAuthorizationResponse> getWorkAuthorization(
            @PathVariable Long candidateId, @PathVariable Long candidateWorkAuthorizationId
    ) {
        var response = candidateWorkAuthorizationService.getWorkAuthorization(candidateId, candidateWorkAuthorizationId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CandidateWorkAuthorizationResponse>> getAllWorkAuthorizations(
            @PathVariable Long candidateId
    ) {
        var response = candidateWorkAuthorizationService.getAllWorkAuthorizations(candidateId);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{candidateWorkAuthorizationId}")
    public ResponseEntity<CandidateWorkAuthorizationResponse> updateWorkAuthorization(
            @PathVariable Long candidateId, @PathVariable Long candidateWorkAuthorizationId,
            @Valid @RequestBody UpdateCandidateWorkAuthorizationRequest request
            ) {
        var response = candidateWorkAuthorizationService.updateWorkAuthorization(
                candidateId, candidateWorkAuthorizationId, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{candidateWorkAuthorizationId}")
    public ResponseEntity<Void> deleteWorkAuthorization(
            @PathVariable Long candidateId, @PathVariable Long candidateWorkAuthorizationId
    ) {
        candidateWorkAuthorizationService.deleteWorkAuthorization(candidateId, candidateWorkAuthorizationId);

        return ResponseEntity.noContent().build();
    }
}
