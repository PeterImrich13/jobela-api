package com.jobela.jobela_api.employer.service.profile;

import com.jobela.jobela_api.employer.dto.response.profile.EmployerPublicProfileResponse;

import java.util.List;

public interface EmployerPublicProfileService {
    EmployerPublicProfileResponse getEmployerPublicProfile(Long employerId);
    List<EmployerPublicProfileResponse> getAllEmployerPublicProfiles();
}
