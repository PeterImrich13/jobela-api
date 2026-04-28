package com.jobela.jobela_api.candidate.controller.workexperience;

import com.jobela.jobela_api.candidate.dto.request.workexperience.CreateCandidateWorkExperienceRequest;
import com.jobela.jobela_api.candidate.dto.request.workexperience.UpdateCandidateWorkExperienceRequest;
import com.jobela.jobela_api.candidate.dto.response.workexperience.CandidateWorkExperienceResponse;
import com.jobela.jobela_api.candidate.service.workexperience.CandidateWorkExperienceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/candidates/{candidateId}/work-experiences")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN') or (hasRole('CANDIDATE') and @candidateSecurity.isOwner(#candidateId, authentication))")
public class CandidateWorkExperienceController {

    private final CandidateWorkExperienceService candidateWorkExperienceService;

    @PostMapping
    public ResponseEntity<CandidateWorkExperienceResponse> createWorkExperience(
            @PathVariable Long candidateId, @Valid @RequestBody CreateCandidateWorkExperienceRequest request
            ) {
        var response = candidateWorkExperienceService.createWorkExperience(candidateId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{candidateWorkExperienceId}")
    public ResponseEntity<CandidateWorkExperienceResponse> getWorkExperience(
            @PathVariable Long candidateId, @PathVariable Long candidateWorkExperienceId
    ) {
        var response = candidateWorkExperienceService.getWorkExperience(candidateId, candidateWorkExperienceId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CandidateWorkExperienceResponse>> getAllWorkExperiences(@PathVariable Long candidateId) {
        var response = candidateWorkExperienceService.getAllWorkExperiences(candidateId);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{candidateWorkExperienceId}")
    public ResponseEntity<CandidateWorkExperienceResponse> updateWorkExperience(
            @PathVariable Long candidateId, @PathVariable Long candidateWorkExperienceId,
            @Valid @RequestBody UpdateCandidateWorkExperienceRequest request
            ) {
        var response = candidateWorkExperienceService.updateWorkExperience(
                candidateId, candidateWorkExperienceId, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{candidateWorkExperienceId}")
    public ResponseEntity<Void> deleteWorkExperience(
            @PathVariable Long candidateId, @PathVariable Long candidateWorkExperienceId
    ) {
        candidateWorkExperienceService.deleteWorkExperience(candidateId, candidateWorkExperienceId);

        return ResponseEntity.noContent().build();
    }
}
