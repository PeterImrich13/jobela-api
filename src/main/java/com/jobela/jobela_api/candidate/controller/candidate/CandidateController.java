package com.jobela.jobela_api.candidate.controller.candidate;


import com.jobela.jobela_api.candidate.dto.request.candidate.CreateCandidateRequest;
import com.jobela.jobela_api.candidate.dto.request.candidate.UpdateCandidateRequest;
import com.jobela.jobela_api.candidate.dto.response.candidate.CandidateResponse;
import com.jobela.jobela_api.candidate.service.candidate.CandidateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/candidates")
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;

    @PostMapping("/user/{userId}")
    public ResponseEntity<CandidateResponse> createCandidate(@PathVariable Long userId,
                                                             @Valid @RequestBody CreateCandidateRequest request) {
        var response = candidateService.createCandidate(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{candidateId}")
    public ResponseEntity<CandidateResponse> getCandidateById(@PathVariable Long candidateId) {
        var response = candidateService.getCandidateById(candidateId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<CandidateResponse> getCandidateByUserId(@PathVariable Long userId) {

        var response = candidateService.getCandidateByUserId(userId);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{candidateId}")
    public ResponseEntity<CandidateResponse> updateCandidate(@PathVariable Long candidateId,@Valid @RequestBody UpdateCandidateRequest request) {

        var response = candidateService.updateCandidate(candidateId, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{candidateId}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long candidateId) {
         candidateService.deleteCandidate(candidateId);
         return ResponseEntity.noContent().build();
    }

}
