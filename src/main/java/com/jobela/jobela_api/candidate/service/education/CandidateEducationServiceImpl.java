package com.jobela.jobela_api.candidate.service.education;


import com.jobela.jobela_api.candidate.dto.request.education.CreateCandidateEducationRequest;
import com.jobela.jobela_api.candidate.dto.request.education.UpdateCandidateEducationRequest;
import com.jobela.jobela_api.candidate.dto.response.education.CandidateEducationResponse;
import com.jobela.jobela_api.candidate.entity.CandidateEducation;
import com.jobela.jobela_api.candidate.mapper.CandidateEducationMapper;
import com.jobela.jobela_api.candidate.repository.CandidateEducationRepository;
import com.jobela.jobela_api.candidate.repository.CandidateRepository;
import com.jobela.jobela_api.common.exception.BadRequestException;
import com.jobela.jobela_api.common.exception.CandidateEducationAlreadyExistsException;
import com.jobela.jobela_api.common.exception.CandidateEducationNotFound;
import com.jobela.jobela_api.common.exception.CandidateNotFoundException;
import com.jobela.jobela_api.common.mapper.StringMapperHelper;
import com.jobela.jobela_api.candidate.entity.Candidate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CandidateEducationServiceImpl implements CandidateEducationService {

    private final CandidateEducationRepository candidateEducationRepository;
    private final CandidateEducationMapper candidateEducationMapper;
    private final CandidateRepository candidateRepository;
    private final StringMapperHelper stringMapperHelper;

    @Override
    public CandidateEducationResponse createCandidateEducation(
            Long candidateId, CreateCandidateEducationRequest request) {
        log.info("Creating candidate education for candidateId={}", candidateId);

        var candidate = getCandidateByIdOrThrow(candidateId);
        var cleanedSchoolName = stringMapperHelper.clean(request.schoolName());
        var cleanedDegree = stringMapperHelper.clean(request.degree());

        if (cleanedSchoolName == null || cleanedSchoolName.isEmpty()) {
            throw new BadRequestException("School name cannot be blank");
        }

        if (cleanedDegree == null || cleanedDegree.isEmpty()) {
            throw  new BadRequestException("Degree cannot be blank");
        }

        if (candidateEducationRepository.existsByCandidateIdAndSchoolNameIgnoreCaseAndDegreeIgnoreCase(
                candidateId, cleanedSchoolName, cleanedDegree)) {
            throw new CandidateEducationAlreadyExistsException(
                    "Candidate education with school name: " + cleanedSchoolName + " and degree: " +
                            cleanedDegree + " already exists for candidateId: " + candidateId);
        }

        var education = candidateEducationMapper.toEntity(request);
        education.setCandidate(candidate);

        validateRequiredFields(education);
        validateEducationDates(education);

        var savedEducation = candidateEducationRepository.save(education);

        log.info("Candidate education created successfully with id={} for candidateId={}",
                savedEducation.getId(), candidateId);

        return candidateEducationMapper.toResponse(savedEducation);
    }

    @Override
    @Transactional(readOnly = true)
    public CandidateEducationResponse getCandidateEducationByCandidateId(Long candidateId, Long candidateEducationId) {
        log.info("Fetching candidate education with id={} for candidateId={}", candidateEducationId, candidateId);

    var education = getCandidateEducationByIdOrThrow(candidateId, candidateEducationId);

    return candidateEducationMapper.toResponse(education);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CandidateEducationResponse> getAllEducationsByCandidateId(Long candidateId) {
        log.info("Fetching all educations for candidateId={}", candidateId);

       getCandidateByIdOrThrow(candidateId);

       var educations = candidateEducationRepository.findAllByCandidateId(candidateId);

       return educations.stream()
               .map(candidateEducationMapper::toResponse)
               .toList();
    }

    @Override
    public CandidateEducationResponse updateCandidateEducation(Long candidateId, Long candidateEducationId, UpdateCandidateEducationRequest request) {
        log.info("Updating candidate education with id={} for candidateId={}", candidateEducationId, candidateId);

       var education = getCandidateEducationByIdOrThrow(candidateId, candidateEducationId);

       var cleanedSchoolName = stringMapperHelper.clean(request.schoolName());
       var cleanedDegree = stringMapperHelper.clean(request.degree());

       var finalSchoolName = cleanedSchoolName != null ? cleanedSchoolName : education.getSchoolName();
       var finalDegree = cleanedDegree != null ? cleanedDegree : education.getDegree();

       if (finalSchoolName == null || finalSchoolName.isBlank()) {
           throw new BadRequestException("School name cannot be blank");
       }

       if (finalDegree == null || finalDegree.isBlank()) {
           throw new BadRequestException("Degree cannot be blank");
       }

       if (candidateEducationRepository.existsByCandidateIdAndSchoolNameIgnoreCaseAndDegreeIgnoreCaseAndIdNot(
               candidateId, finalSchoolName, finalDegree, candidateEducationId
       )) {
           throw new CandidateEducationAlreadyExistsException("Candidate education with school name " + finalSchoolName +
                   " and degree " + finalDegree + " for candidateId " + candidateId + " already exists");
       }

       candidateEducationMapper.updateEntity(request, education);

       validateRequiredFields(education);
       validateEducationDates(education);

       var updatedEducation = candidateEducationRepository.save(education);

       log.info("Candidate education updated successfully with id={} for candidateId={}",
               updatedEducation.getId(), candidateId);

       return candidateEducationMapper.toResponse(updatedEducation);
    }

    @Override
    public void deleteCandidateEducation(Long candidateId, Long candidateEducationId) {
        log.info("Deleting candidate education with id={} for candidateId={}", candidateEducationId, candidateId);

        var education = getCandidateEducationByIdOrThrow(candidateId, candidateEducationId);

        candidateEducationRepository.delete(education);

        log.info("Candidate education deleted successfully with id={} for candidateId={}", candidateEducationId, candidateId);
    }

    private CandidateEducation getCandidateEducationByIdOrThrow(Long candidateId, Long candidateEducationId) {
        return candidateEducationRepository.findByIdAndCandidateId(candidateEducationId, candidateId)
                .orElseThrow(() -> new CandidateEducationNotFound("Candidate education with id " + candidateEducationId +
                        " not found for candidateId " + candidateEducationId));
    }
    private Candidate getCandidateByIdOrThrow(Long candidateId) {
        return candidateRepository.findById(candidateId)
                .orElseThrow(() -> new CandidateNotFoundException("Candidate not found with id " + candidateId));
    }

    private void validateRequiredFields(CandidateEducation candidateEducation) {
        if (candidateEducation.getSchoolName() == null || candidateEducation.getSchoolName().isBlank()) {
            throw new BadRequestException("School name cannot be blank");
        }

        if (candidateEducation.getDegree() == null || candidateEducation.getDegree().isBlank()) {
            throw new BadRequestException("Degree cannot be blank");
        }
    }

    private void validateEducationDates(CandidateEducation candidateEducation) {
        if (candidateEducation.getEndDate() != null && candidateEducation.getEndDate().isBefore(candidateEducation.getStartDate())) {
            throw new BadRequestException("End date cannot be before start date");
        }

        if (Boolean.TRUE.equals(candidateEducation.getCurrentlyStudying()) && candidateEducation.getEndDate() != null) {
            throw new BadRequestException("End date must be null when currently studying is true");
        }

        if (Boolean.FALSE.equals(candidateEducation.getCurrentlyStudying()) && candidateEducation.getEndDate() == null) {
            throw new BadRequestException("End date cannot be null when currently studying is false");
        }
    }
}
