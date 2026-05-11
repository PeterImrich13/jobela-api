package com.jobela.jobela_api.employer.controller;

import com.jobela.jobela_api.employer.dto.request.CreateEmployerRequest;
import com.jobela.jobela_api.employer.dto.request.UpdateEmployerRequest;
import com.jobela.jobela_api.employer.dto.response.EmployerResponse;
import com.jobela.jobela_api.employer.service.EmployerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employers")
@RequiredArgsConstructor
public class EmployerController {

    private final EmployerService employerService;

    @PreAuthorize("hasRole('ADMIN') or (hasRole('EMPLOYER') " +
            "and @employerSecurity.isCurrentUser(#userId, authentication))")
    @PostMapping("/user/{userId}")
    public ResponseEntity<EmployerResponse> createEmployer(
            @PathVariable Long userId, @Valid @RequestBody CreateEmployerRequest request
            ) {
        var response = employerService.createEmployer(userId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('EMPLOYER') " +
            "and @employerSecurity.isOwner(#employerId, authentication))")
    @GetMapping("/{employerId}")
    public ResponseEntity<EmployerResponse> getEmployerById(@PathVariable Long employerId) {
        var response = employerService.getEmployerById(employerId);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('EMPLOYER') " +
            "and @employerSecurity.isCurrentUser(#userId, authentication))")
    @GetMapping("/user/{userId}")
    public ResponseEntity<EmployerResponse> getEmployerByUserId(@PathVariable Long userId) {
        var response = employerService.getEmployerByUserId(userId);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<EmployerResponse>> getAllEmployers(@PageableDefault(
            size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        var response = employerService.getAllEmployers(pageable);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN') or @employerSecurity.isOwner(#employerId, authentication)")
    @PatchMapping("/{employerId}")
    public ResponseEntity<EmployerResponse> updateEmployer(
            @PathVariable Long employerId, @Valid @RequestBody UpdateEmployerRequest request
    ) {
        var response = employerService.updateEmployer(employerId, request);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN') or @employerSecurity.isOwner(#employerId, authentication)")
    @DeleteMapping("/{employerId}")
    public ResponseEntity<Void> deleteEmployer(@PathVariable Long employerId) {
        employerService.deleteEmployer(employerId);

        return ResponseEntity.noContent().build();
    }
}
