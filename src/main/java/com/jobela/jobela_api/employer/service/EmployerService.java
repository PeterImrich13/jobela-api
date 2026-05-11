package com.jobela.jobela_api.employer.service;

import com.jobela.jobela_api.employer.dto.request.CreateEmployerRequest;
import com.jobela.jobela_api.employer.dto.request.UpdateEmployerRequest;
import com.jobela.jobela_api.employer.dto.response.EmployerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployerService {
    EmployerResponse createEmployer(Long userId, CreateEmployerRequest request);
    EmployerResponse getEmployerById(Long employerId);
    EmployerResponse getEmployerByUserId(Long userId);
    Page<EmployerResponse> getAllEmployers(Pageable pageable);
    EmployerResponse updateEmployer(Long employerId, UpdateEmployerRequest request);
    void deleteEmployer(Long employerId);
}
