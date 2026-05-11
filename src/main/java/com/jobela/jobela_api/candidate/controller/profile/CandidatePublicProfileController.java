package com.jobela.jobela_api.candidate.controller.profile;

import com.jobela.jobela_api.candidate.dto.response.profile.CandidatePublicProfileResponse;
import com.jobela.jobela_api.candidate.service.profile.CandidatePublicProfileService;
import com.jobela.jobela_api.common.pagination.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Page<CandidatePublicProfileResponse>> getAllCandidatePublicProfiles(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {

        var response = candidatePublicProfileService.getAllCandidatePublicProfiles(pageable);

        return ResponseEntity.ok(response);
    }
}
