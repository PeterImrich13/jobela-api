package com.jobela.jobela_api.employer.service;

import com.jobela.jobela_api.employer.dto.request.CreateEmployerRequest;
import com.jobela.jobela_api.employer.dto.request.UpdateEmployerRequest;
import com.jobela.jobela_api.employer.dto.response.EmployerResponse;
import java.util.List;

public interface EmployerService {
    EmployerResponse createEmployer(Long userId, CreateEmployerRequest request);
    EmployerResponse getEmployerById(Long employerId);
    EmployerResponse getEmployerByUserId(Long userId);
    List<EmployerResponse> getAllEmployers();
    EmployerResponse updateEmployer(Long employerId, UpdateEmployerRequest request);
    void deleteEmployer(Long employerId);
}
