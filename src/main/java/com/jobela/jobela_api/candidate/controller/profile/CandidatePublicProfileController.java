package com.jobela.jobela_api.candidate.controller.profile;

import com.jobela.jobela_api.candidate.dto.request.profile.CandidatePublicProfileSearchCriteria;
import com.jobela.jobela_api.candidate.dto.response.profile.CandidatePublicProfileResponse;
import com.jobela.jobela_api.candidate.dto.response.profile.CandidatePublicProfileSummaryResponse;
import com.jobela.jobela_api.candidate.service.profile.CandidatePublicProfileService;
import com.jobela.jobela_api.common.enums.AuthorizationType;
import com.jobela.jobela_api.common.enums.CandidateTargetPosition;
import com.jobela.jobela_api.common.enums.LanguageLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/candidates/public")
@RequiredArgsConstructor
public class CandidatePublicProfileController {

    private final CandidatePublicProfileService candidatePublicProfileService;

    @GetMapping("/{candidateId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYER')")
    public ResponseEntity<CandidatePublicProfileResponse> getCandidatePublicProfile(
            @PathVariable Long candidateId
    ) {
        var response = candidatePublicProfileService.getCandidatePublicProfile(candidateId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYER')")
    public ResponseEntity<Page<CandidatePublicProfileSummaryResponse>> getAllCandidatePublicProfiles(
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String skill,
            @RequestParam(required = false) String language,
            @RequestParam(required = false) Boolean openToWork,
            @RequestParam(required = false) CandidateTargetPosition targetPosition,
            @RequestParam(required = false) LanguageLevel level,
            @RequestParam(required = false) AuthorizationType authorizationType,
            @RequestParam(required = false) Boolean sponsorshipRequired,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {

        var criteria = new CandidatePublicProfileSearchCriteria(
                country, city, search, skill, language, openToWork, targetPosition, level,
                authorizationType, sponsorshipRequired);

        var response = candidatePublicProfileService.getAllCandidatePublicProfiles(criteria, pageable);

        return ResponseEntity.ok(response);
    }
}
