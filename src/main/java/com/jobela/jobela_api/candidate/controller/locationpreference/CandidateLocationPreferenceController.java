package com.jobela.jobela_api.candidate.controller.locationpreference;


import com.jobela.jobela_api.candidate.dto.request.locationpreference.CreateCandidateLocationPreferenceRequest;
import com.jobela.jobela_api.candidate.dto.request.locationpreference.UpdateCandidateLocationPreferenceRequest;
import com.jobela.jobela_api.candidate.dto.response.locationpreference.CandidateLocationPreferenceResponse;
import com.jobela.jobela_api.candidate.service.locationpreference.CandidateLocationPreferenceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/candidates/{candidateId}/location-preferences")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN') or (hasRole('CANDIDATE') and @candidateSecurity.isOwner(#candidateId, authentication))")
public class CandidateLocationPreferenceController {

    private final CandidateLocationPreferenceService candidateLocationPreferenceService;

    @PostMapping
    public ResponseEntity<CandidateLocationPreferenceResponse> createLocationPreference(
            @PathVariable Long candidateId,
            @Valid @RequestBody CreateCandidateLocationPreferenceRequest request
            ) {
        var response = candidateLocationPreferenceService.createLocationPreference(candidateId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{candidateLocationPreferenceId}")
    public ResponseEntity<CandidateLocationPreferenceResponse> getLocationPreference(
            @PathVariable Long candidateId, @PathVariable Long candidateLocationPreferenceId
    ) {
        var response = candidateLocationPreferenceService.getLocationPreference(
                candidateId, candidateLocationPreferenceId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CandidateLocationPreferenceResponse>> getAllLocationPreferences(
            @PathVariable Long candidateId
    ) {
        var response = candidateLocationPreferenceService.getAllLocationPreferences(candidateId);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{candidateLocationPreferenceId}")
    public ResponseEntity<CandidateLocationPreferenceResponse> updateLocationPreference(
            @PathVariable Long candidateId, @PathVariable Long candidateLocationPreferenceId,
            @Valid @RequestBody UpdateCandidateLocationPreferenceRequest request
            ) {
        var response = candidateLocationPreferenceService.updateLocationPreference(
                candidateId, candidateLocationPreferenceId, request
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{candidateLocationPreferenceId}")
    public ResponseEntity<Void> deleteLocationPreference(
            @PathVariable Long candidateId, @PathVariable Long candidateLocationPreferenceId
    ) {
        candidateLocationPreferenceService.deleteLocationPreference(candidateId, candidateLocationPreferenceId);

        return ResponseEntity.noContent().build();
    }
}
