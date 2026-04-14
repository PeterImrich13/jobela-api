package com.jobela.jobela_api.candidate.controller.preference;


import com.jobela.jobela_api.candidate.dto.request.preference.CreateCandidatePreferenceRequest;
import com.jobela.jobela_api.candidate.dto.request.preference.UpdateCandidatePreferenceRequest;
import com.jobela.jobela_api.candidate.dto.response.preference.CandidatePreferenceResponse;
import com.jobela.jobela_api.candidate.service.preference.CandidatePreferenceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/candidates/{candidateId}/preferences")
@RequiredArgsConstructor
public class CandidatePreferenceController {

    private final CandidatePreferenceService candidatePreferenceService;

    @PostMapping
    public ResponseEntity<CandidatePreferenceResponse> createCandidatePreference(
            @PathVariable Long candidateId, @Valid @RequestBody CreateCandidatePreferenceRequest request
            ) {
        var response = candidatePreferenceService.createCandidatePreference(candidateId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{candidatePreferenceId}")
    public ResponseEntity<CandidatePreferenceResponse> getCandidatePreference(
            @PathVariable Long candidateId, @PathVariable Long candidatePreferenceId
    ) {
        var response = candidatePreferenceService.getCandidatePreference(candidateId, candidatePreferenceId);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{candidatePreferenceId}")
    public ResponseEntity<CandidatePreferenceResponse> updateCandidatePreference(
            @PathVariable Long candidateId, @PathVariable Long candidatePreferenceId,
            @Valid @RequestBody UpdateCandidatePreferenceRequest request
            ) {
        var response = candidatePreferenceService.updateCandidatePreference(
                candidateId, candidatePreferenceId, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{candidatePreferenceId}")
    public ResponseEntity<Void> deleteCandidatePreference(
            @PathVariable Long candidateId, @PathVariable Long candidatePreferenceId) {
        candidatePreferenceService.deleteCandidatePreference(candidateId, candidatePreferenceId);

        return ResponseEntity.noContent().build();
    }
}
