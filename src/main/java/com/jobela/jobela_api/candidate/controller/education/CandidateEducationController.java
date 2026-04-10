package com.jobela.jobela_api.candidate.controller.education;


import com.jobela.jobela_api.candidate.dto.request.education.CreateCandidateEducationRequest;
import com.jobela.jobela_api.candidate.dto.request.education.UpdateCandidateEducationRequest;
import com.jobela.jobela_api.candidate.dto.response.education.CandidateEducationResponse;
import com.jobela.jobela_api.candidate.service.education.CandidateEducationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidates/{candidateId}/educations")
@RequiredArgsConstructor
public class CandidateEducationController {

    private final CandidateEducationService candidateEducationService;

    @PostMapping
    public ResponseEntity<CandidateEducationResponse> createCandidateEducation(
            @PathVariable Long candidateId, @Valid @RequestBody CreateCandidateEducationRequest request
            ) {

        var response = candidateEducationService.createCandidateEducation(candidateId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{candidateEducationId}")
    public ResponseEntity<CandidateEducationResponse> getCandidateEducationById(
            @PathVariable Long candidateId, @PathVariable Long candidateEducationId
    ) {
        var response = candidateEducationService.getCandidateEducationByCandidateId(candidateId, candidateEducationId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CandidateEducationResponse>> getAllEducationsByCandidateId(
            @PathVariable Long candidateId
    ) {
        var response = candidateEducationService.getAllEducationsByCandidateId(candidateId);
        return ResponseEntity.ok(response);
    }

   @PatchMapping("/{candidateEducationId}")
   public ResponseEntity<CandidateEducationResponse> updateCandidateEducation(
           @PathVariable Long candidateId,
           @PathVariable Long candidateEducationId,
           @Valid @RequestBody UpdateCandidateEducationRequest request
           ) {
        var response = candidateEducationService.updateCandidateEducation(candidateId, candidateEducationId, request);

        return ResponseEntity.ok(response);
   }

   @DeleteMapping("/{candidateEducationId}")
    public ResponseEntity<Void> deleteCandidateEducation(
            @PathVariable Long candidateId, @PathVariable Long candidateEducationId
   ) {
        candidateEducationService.deleteCandidateEducation(candidateId, candidateEducationId);

        return ResponseEntity.noContent().build();
   }
}
