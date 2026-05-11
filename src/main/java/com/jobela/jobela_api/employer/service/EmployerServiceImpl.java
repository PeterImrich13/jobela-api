package com.jobela.jobela_api.employer.service;

import com.jobela.jobela_api.common.exception.BadRequestException;
import com.jobela.jobela_api.common.exception.EmployerAlreadyExistsException;
import com.jobela.jobela_api.common.exception.EmployerNotFoundException;
import com.jobela.jobela_api.common.exception.UserNotFoundException;
import com.jobela.jobela_api.common.pagination.PaginationUtils;
import com.jobela.jobela_api.common.sort.EmployerSortFields;
import com.jobela.jobela_api.employer.dto.request.CreateEmployerRequest;
import com.jobela.jobela_api.employer.dto.request.UpdateEmployerRequest;
import com.jobela.jobela_api.employer.dto.response.EmployerResponse;
import com.jobela.jobela_api.employer.entity.Employer;
import com.jobela.jobela_api.employer.mapper.EmployerMapper;
import com.jobela.jobela_api.employer.repository.EmployerRepository;
import com.jobela.jobela_api.user.entity.User;
import com.jobela.jobela_api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class EmployerServiceImpl implements EmployerService {

    private final UserRepository userRepository;
    private final EmployerRepository employerRepository;
    private final EmployerMapper employerMapper;

    @Override
    public EmployerResponse createEmployer(Long userId, CreateEmployerRequest request) {
        log.info("Creating employer for userId={}", userId);

        var user = getUserOrThrow(userId);

        if (employerRepository.existsByUserId(userId)) {
            throw new EmployerAlreadyExistsException("Employer already exists with userId " + userId);
        }

        var employer = employerMapper.toEntity(request);
        employer.setUser(user);

        validateRequiredFields(employer);

        var savedEmployer = employerRepository.save(employer);

        log.info("Employer successfully created for userId={}", userId);

        return employerMapper.toResponse(savedEmployer);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployerResponse getEmployerById(Long employerId) {
        log.info("Fetching employer with id={}", employerId);

        var employer = getEmployerOrThrow(employerId);

        return employerMapper.toResponse(employer);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployerResponse getEmployerByUserId(Long userId) {
        log.info("Fetching employer for userId={}", userId);

        var employer = getEmployerByUserIdOrThrow(userId);

        return employerMapper.toResponse(employer);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmployerResponse> getAllEmployers(Pageable pageable) {
        log.info("Fetching all employers with pagination");

        PaginationUtils.validatePageable(pageable, EmployerSortFields.ALLOWED);

        return employerRepository.findAll(pageable)
                .map(employerMapper::toResponse);
    }

    @Override
    public EmployerResponse updateEmployer(Long employerId, UpdateEmployerRequest request) {
        log.info("Updating employer with id={}", employerId);

        var employer = getEmployerOrThrow(employerId);

        employerMapper.updateEmployerFromRequest(request, employer);

        validateUpdatedRequiredFields(request, employer);

        var updatedEmployer = employerRepository.save(employer);

        log.info("Employer with id={} successfully updated", employerId);

        return employerMapper.toResponse(updatedEmployer);
    }

    @Override
    public void deleteEmployer(Long employerId) {
        log.info("Deleting employer with id={}", employerId);

        var employer = getEmployerOrThrow(employerId);

        employerRepository.delete(employer);

        log.info("Employer with id={} successfully deleted", employerId);
    }

    private User getUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found for userId: " + userId));
    }

    private Employer getEmployerOrThrow(Long employerId) {
        return employerRepository.findById(employerId)
                .orElseThrow(() -> new EmployerNotFoundException("Employer not found with id: " + employerId));
    }

    private Employer getEmployerByUserIdOrThrow(Long userId) {
        return employerRepository.findByUserId(userId)
                .orElseThrow(() -> new EmployerNotFoundException("Employer not found for userId: " + userId));
    }

    private void validateRequiredFields(Employer employer) {
        if (employer.getCompanyName() == null || employer.getCompanyName().isBlank()) {
            throw new BadRequestException("Company name cannot be blank");
        }

        if (employer.getCompanyDescription() == null || employer.getCompanyDescription().isBlank()) {
            throw new BadRequestException("Company description cannot be blank");
        }

        if (employer.getIndustry() == null) {
            throw new BadRequestException("Industry cannot be null");
        }
    }

    private void validateUpdatedRequiredFields(UpdateEmployerRequest request, Employer employer) {
        if (request.companyName() != null
                && (employer.getCompanyName() == null || employer.getCompanyName().isBlank())) {
            throw new BadRequestException("Company name cannot be blank");
        }

        if (request.companyDescription() != null
                && (employer.getCompanyDescription() == null || employer.getCompanyDescription().isBlank())) {
            throw new BadRequestException("Company description cannot be blank");
        }
    }
}
