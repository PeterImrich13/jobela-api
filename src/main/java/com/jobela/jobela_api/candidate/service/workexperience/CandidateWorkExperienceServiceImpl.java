package com.jobela.jobela_api.candidate.service.workexperience;

import com.jobela.jobela_api.candidate.dto.request.workexperience.CreateCandidateWorkExperienceRequest;
import com.jobela.jobela_api.candidate.dto.request.workexperience.UpdateCandidateWorkExperienceRequest;
import com.jobela.jobela_api.candidate.dto.response.workexperience.CandidateWorkExperienceResponse;
import com.jobela.jobela_api.candidate.entity.Candidate;
import com.jobela.jobela_api.candidate.entity.CandidateWorkExperience;
import com.jobela.jobela_api.candidate.mapper.CandidateWorkExperienceMapper;
import com.jobela.jobela_api.candidate.repository.CandidateRepository;
import com.jobela.jobela_api.candidate.repository.CandidateWorkExperienceRepository;
import com.jobela.jobela_api.common.exception.BadRequestException;
import com.jobela.jobela_api.common.exception.CandidateNotFoundException;
import com.jobela.jobela_api.common.exception.CandidateWorkExperienceAlreadyExistsException;
import com.jobela.jobela_api.common.exception.CandidateWorkExperienceNotFoundException;
import com.jobela.jobela_api.common.mapper.StringMapperHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CandidateWorkExperienceServiceImpl implements CandidateWorkExperienceService {

    private final CandidateRepository candidateRepository;
    private final CandidateWorkExperienceRepository candidateWorkExperienceRepository;
    private final CandidateWorkExperienceMapper candidateWorkExperienceMapper;
    private final StringMapperHelper stringMapperHelper;

    @Override
    public CandidateWorkExperienceResponse createWorkExperience(
            Long candidateId, CreateCandidateWorkExperienceRequest request) {
        log.info("Creating work experience for candidateId={}", candidateId);

        var candidate = getCandidateOrThrow(candidateId);

        var cleanedCompanyName = stringMapperHelper.clean(request.companyName());
        var cleanedJobTitle = stringMapperHelper.clean(request.jobTitle());

        validateCompanyName(cleanedCompanyName);
        validateJobTitle(cleanedJobTitle);
        validateWorkExperienceDates(request.startDate(), request.endDate(), request.currentlyWorking());
        validateDuplicateWorkExperience(candidateId, cleanedCompanyName, cleanedJobTitle, request.startDate());

        var workExperience = candidateWorkExperienceMapper.toEntity(request);
        workExperience.setCandidate(candidate);
        workExperience.setCompanyName(cleanedCompanyName);
        workExperience.setJobTitle(cleanedJobTitle);

        var savedWorkExperience = candidateWorkExperienceRepository.save(workExperience);

        log.info("Work experience successfully created for candidateId={}", candidateId);

        return candidateWorkExperienceMapper.toResponse(savedWorkExperience);
    }

    @Override
    @Transactional(readOnly = true)
    public CandidateWorkExperienceResponse getWorkExperience(Long candidateId, Long candidateWorkExperienceId) {
        log.info("Fetching work experience with id={} for candidateId={}", candidateWorkExperienceId, candidateId);

        getCandidateOrThrow(candidateId);

        var workExperience = getWorkExperienceOrThrow(candidateId, candidateWorkExperienceId);

        return candidateWorkExperienceMapper.toResponse(workExperience);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CandidateWorkExperienceResponse> getAllWorkExperiences(Long candidateId) {
        log.info("Fetching all work experiences for candidateId={}", candidateId);

        getCandidateOrThrow(candidateId);

        var workExperiences = candidateWorkExperienceRepository.findAllByCandidateId(candidateId);

        return workExperiences.stream()
                .map(candidateWorkExperienceMapper::toResponse)
                .toList();
    }

    @Override
    public CandidateWorkExperienceResponse updateWorkExperience(
            Long candidateId, Long candidateWorkExperienceId, UpdateCandidateWorkExperienceRequest request) {
        log.info("Updating work experience with id={} for candidateId={}", candidateWorkExperienceId, candidateId);

        getCandidateOrThrow(candidateId);

        var workExperience = getWorkExperienceOrThrow(candidateId, candidateWorkExperienceId);

        var finalCompanyName = request.companyName() != null
                ? stringMapperHelper.clean(request.companyName())
                : workExperience.getCompanyName();

        var finalJobTitle = request.jobTitle() != null
                ? stringMapperHelper.clean(request.jobTitle())
                : workExperience.getJobTitle();

        var finalStartDate = request.startDate() != null
                ? request.startDate()
                : workExperience.getStartDate();

        var finalCurrentlyWorking = request.currentlyWorking() != null
                ? request.currentlyWorking()
                : workExperience.isCurrentlyWorking();

        var finalEndDate = finalCurrentlyWorking
                ? null
                : (request.endDate() != null ? request.endDate() : workExperience.getEndDate());

        validateCompanyName(finalCompanyName);
        validateJobTitle(finalJobTitle);
        validateWorkExperienceDates(finalStartDate, finalEndDate, finalCurrentlyWorking);
        validateDuplicateWorkExperienceForUpdate(
                candidateId, finalCompanyName, finalJobTitle, finalStartDate, candidateWorkExperienceId);

        candidateWorkExperienceMapper.updateEntity(request, workExperience);
        workExperience.setCompanyName(finalCompanyName);
        workExperience.setJobTitle(finalJobTitle);
        workExperience.setCurrentlyWorking(finalCurrentlyWorking);
        workExperience.setEndDate(finalEndDate);

        var updatedWorkExperience = candidateWorkExperienceRepository.save(workExperience);

        log.info("Work experience successfully updated with id={} for candidateId={}",
                candidateWorkExperienceId, candidateId);

        return candidateWorkExperienceMapper.toResponse(updatedWorkExperience);
    }

    @Override
    public void deleteWorkExperience(Long candidateId, Long candidateWorkExperienceId) {
        log.info("Deleting work experience with id={} for candidateId={}", candidateWorkExperienceId, candidateId);

        getCandidateOrThrow(candidateId);

        var workExperience = getWorkExperienceOrThrow(candidateId, candidateWorkExperienceId);

        candidateWorkExperienceRepository.delete(workExperience);

        log.info("Work experience with id={} successfully deleted for candidateId={}", candidateWorkExperienceId, candidateId);
    }

    private Candidate getCandidateOrThrow(Long candidateId) {
        return candidateRepository.findById(candidateId)
                .orElseThrow(() -> new CandidateNotFoundException("Candidate not found with id " + candidateId));
    }

    private CandidateWorkExperience getWorkExperienceOrThrow(Long candidateId, Long candidateWorkExperienceId) {
        return candidateWorkExperienceRepository.findByIdAndCandidateId(candidateWorkExperienceId, candidateId)
                .orElseThrow(() -> new CandidateWorkExperienceNotFoundException("Work experience with id "
                + candidateWorkExperienceId + " not found for candidateId " + candidateId));
    }

    private void validateCompanyName(String cleanedCompanyName) {
        if (cleanedCompanyName == null || cleanedCompanyName.isBlank()) {
            throw new BadRequestException("Company name cannot be blank");
        }
    }

    private void validateJobTitle(String cleanedJobTitle) {
        if (cleanedJobTitle == null || cleanedJobTitle.isBlank()) {
            throw new BadRequestException("Job title cannot be blank");
        }
    }

    private void validateWorkExperienceDates(LocalDate startDate, LocalDate endDate, Boolean currentlyWorking) {
        if (Boolean.TRUE.equals(currentlyWorking) && endDate != null) {
            throw new BadRequestException("End date must be null when currently working is true");
        }

        if (Boolean.FALSE.equals(currentlyWorking) && endDate == null) {
            throw new BadRequestException("End date is required when currently working is false");
        }

        if (endDate != null && startDate.isAfter(endDate)) {
            throw new BadRequestException("Start date must be before or equal to end date");
        }
    }

    private void validateDuplicateWorkExperience(
            Long candidateId, String companyName, String jobTitle, LocalDate startDate
    ) {

        if (candidateWorkExperienceRepository.existsByCandidateIdAndCompanyNameIgnoreCaseAndJobTitleIgnoreCaseAndStartDate(
                candidateId, companyName, jobTitle, startDate
        )) {
            throw new CandidateWorkExperienceAlreadyExistsException("Work experience with company name "
                    + companyName + " and job title " + jobTitle + " and start date " + startDate +
                    " already exists for candidateId " + candidateId);
        }
    }

    private void validateDuplicateWorkExperienceForUpdate(
            Long candidateId,
            String companyName,
            String jobTitle,
            LocalDate startDate,
            Long candidateWorkExperienceId
    ) {
        if (candidateWorkExperienceRepository
                .existsByCandidateIdAndCompanyNameIgnoreCaseAndJobTitleIgnoreCaseAndStartDateAndIdNot(
                   candidateId, companyName, jobTitle, startDate, candidateWorkExperienceId
                )) {
            throw new CandidateWorkExperienceAlreadyExistsException(
                    "Work experience already exists for company name "
                            + companyName + ", job title " + jobTitle + " and start date " + startDate
            );
        }
    }
}
