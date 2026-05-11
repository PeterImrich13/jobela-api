package com.jobela.jobela_api.employer.service.profile;

import com.jobela.jobela_api.employer.dto.response.profile.EmployerPublicProfileResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployerPublicProfileService {
    EmployerPublicProfileResponse getEmployerPublicProfile(Long employerId);
    Page<EmployerPublicProfileResponse> getAllEmployerPublicProfiles(Pageable pageable);
}
