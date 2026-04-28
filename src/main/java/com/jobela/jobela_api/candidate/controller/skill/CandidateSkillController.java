package com.jobela.jobela_api.candidate.controller.skill;


import com.jobela.jobela_api.candidate.dto.request.skill.CreateCandidateSkillRequest;
import com.jobela.jobela_api.candidate.dto.request.skill.UpdateCandidateSkillRequest;
import com.jobela.jobela_api.candidate.dto.response.skill.CandidateSkillResponse;
import com.jobela.jobela_api.candidate.service.skill.CandidateSkillService;
import com.jobela.jobela_api.common.enums.SkillType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/candidates/{candidateId}/skills")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN') or (hasRole('CANDIDATE') and @candidateSecurity.isOwner(#candidateId, authentication))")
public class CandidateSkillController {

    private final CandidateSkillService candidateSkillService;

    @PostMapping
    public ResponseEntity<CandidateSkillResponse> createSkill(@PathVariable Long candidateId,
                                                              @Valid @RequestBody CreateCandidateSkillRequest request){
        var response = candidateSkillService.createSkill(candidateId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



    @GetMapping
    public ResponseEntity<List<CandidateSkillResponse>> getAllSkills(
            @PathVariable Long candidateId,
            @RequestParam(name = "type", required = false) SkillType skillType
            ) {
        var response = candidateSkillService.getSkills(candidateId, skillType);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{skillId}")
    public ResponseEntity<CandidateSkillResponse> getSkillById(@PathVariable Long candidateId, @PathVariable Long skillId) {

        var response = candidateSkillService.getSkillById(candidateId, skillId);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{skillId}")
    public ResponseEntity<CandidateSkillResponse> updateSkill(@PathVariable Long candidateId, @PathVariable Long skillId,
                                                              @Valid @RequestBody UpdateCandidateSkillRequest request) {
        var response = candidateSkillService.updateSkill(candidateId, skillId, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{skillId}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long candidateId, @PathVariable Long skillId) {
        candidateSkillService.deleteSkill(candidateId, skillId);

        return ResponseEntity.noContent().build();
    }
}
