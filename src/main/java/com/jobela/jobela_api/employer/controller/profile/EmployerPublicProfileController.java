package com.jobela.jobela_api.employer.controller.profile;

import com.jobela.jobela_api.employer.dto.response.profile.EmployerPublicProfileResponse;
import com.jobela.jobela_api.employer.service.profile.EmployerPublicProfileService;
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
@RequestMapping("/api/v1/employers/public")
@RequiredArgsConstructor
public class EmployerPublicProfileController {

    private final EmployerPublicProfileService employerPublicProfileService;

    @GetMapping("/{employerId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CANDIDATE')")
    public ResponseEntity<EmployerPublicProfileResponse> getEmployerPublicProfile(
            @PathVariable Long employerId
    ) {
        var response = employerPublicProfileService.getEmployerPublicProfile(employerId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CANDIDATE')")
    public ResponseEntity<Page<EmployerPublicProfileResponse>> getAllEmployerPublicProfiles(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        var response = employerPublicProfileService.getAllEmployerPublicProfiles(pageable);

        return ResponseEntity.ok(response);
    }
}
