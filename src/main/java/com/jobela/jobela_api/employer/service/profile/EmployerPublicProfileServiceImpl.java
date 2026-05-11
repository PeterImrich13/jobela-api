package com.jobela.jobela_api.employer.service.profile;

import com.jobela.jobela_api.common.exception.EmployerNotFoundException;
import com.jobela.jobela_api.common.pagination.PaginationUtils;
import com.jobela.jobela_api.common.sort.EmployerPublicProfileSortFields;
import com.jobela.jobela_api.employer.dto.response.profile.EmployerPublicProfileResponse;
import com.jobela.jobela_api.employer.entity.Employer;
import com.jobela.jobela_api.employer.repository.EmployerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployerPublicProfileServiceImpl implements EmployerPublicProfileService {

    private final EmployerRepository employerRepository;

    @Override
    public EmployerPublicProfileResponse getEmployerPublicProfile(Long employerId) {
        log.info("Fetching public employer profile for employerId={}", employerId);

        var employer = getEmployerOrThrow(employerId);

        if (!employer.isProfileVisible()) {
            throw new EmployerNotFoundException("Employer not found for id: " + employerId);
        }

        return toPublicProfileResponse(employer);
    }

    @Override
    public Page<EmployerPublicProfileResponse> getAllEmployerPublicProfiles(Pageable pageable) {
        log.info("Fetching all public employer profiles with pagination");

        PaginationUtils.validatePageable(pageable, EmployerPublicProfileSortFields.ALLOWED);

        return employerRepository.findAllByProfileVisibleTrue(pageable)
                .map(this::toPublicProfileResponse);
    }

    private Employer getEmployerOrThrow(Long employerId) {
        return employerRepository.findById(employerId)
                .orElseThrow(() -> new EmployerNotFoundException("Employer not found for id: " + employerId));
    }

    private EmployerPublicProfileResponse toPublicProfileResponse(Employer employer) {
        return new EmployerPublicProfileResponse(
                employer.getCompanyName(),
                employer.getCompanyDescription(),
                employer.getIndustry(),
                employer.getWebsite(),
                employer.getPhone(),
                employer.getContactEmail(),
                employer.getCity(),
                employer.getCountry(),
                employer.getStreet(),
                employer.getStreetNumber(),
                employer.getProfilePhoto(),
                employer.isVerified()
        );
    }
}
