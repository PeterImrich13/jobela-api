package com.jobela.jobela_api.candidate.controller.language;


import com.jobela.jobela_api.candidate.dto.request.language.CreateCandidateLanguageRequest;
import com.jobela.jobela_api.candidate.dto.request.language.UpdateCandidateLanguageRequest;
import com.jobela.jobela_api.candidate.dto.response.language.CandidateLanguageResponse;
import com.jobela.jobela_api.candidate.service.language.CandidateLanguageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/candidates/{candidateId}/languages")
@RequiredArgsConstructor
public class CandidateLanguageController {

    private final CandidateLanguageService candidateLanguageService;

    @PostMapping
    public ResponseEntity<CandidateLanguageResponse> createCandidateLanguage(@PathVariable Long candidateId, @Valid @RequestBody CreateCandidateLanguageRequest request) {
        var response = candidateLanguageService.createCandidateLanguage(candidateId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{languageId}")
    public ResponseEntity<CandidateLanguageResponse> getLanguageById(@PathVariable Long candidateId, @PathVariable Long languageId) {
        var response = candidateLanguageService.getLanguageById(candidateId, languageId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CandidateLanguageResponse>> getAllLanguagesByCandidateId(@PathVariable Long candidateId) {
        var response = candidateLanguageService.getAllLanguagesByCandidateId(candidateId);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{languageId}")
    public ResponseEntity<CandidateLanguageResponse> updateCandidateLanguage(
            @PathVariable Long candidateId, @PathVariable Long languageId,
            @Valid @RequestBody UpdateCandidateLanguageRequest request
            ) {
        var response = candidateLanguageService.updateCandidateLanguage(candidateId, languageId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{languageId}")
    public ResponseEntity<Void> deleteLanguage(@PathVariable Long candidateId, @PathVariable Long languageId) {
        candidateLanguageService.deleteLanguage(candidateId, languageId);
        return ResponseEntity.noContent().build();
    }
}
