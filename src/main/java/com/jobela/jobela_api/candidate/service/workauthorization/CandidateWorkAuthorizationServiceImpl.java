package com.jobela.jobela_api.candidate.service.workauthorization;

import com.jobela.jobela_api.candidate.dto.request.workauthorization.CreateCandidateWorkAuthorizationRequest;
import com.jobela.jobela_api.candidate.dto.request.workauthorization.UpdateCandidateWorkAuthorizationRequest;
import com.jobela.jobela_api.candidate.dto.response.workauthorization.CandidateWorkAuthorizationResponse;
import com.jobela.jobela_api.candidate.entity.Candidate;
import com.jobela.jobela_api.candidate.entity.CandidateWorkAuthorization;
import com.jobela.jobela_api.candidate.mapper.CandidateWorkAuthorizationMapper;
import com.jobela.jobela_api.candidate.repository.CandidateRepository;
import com.jobela.jobela_api.candidate.repository.CandidateWorkAuthorizationRepository;
import com.jobela.jobela_api.common.enums.AuthorizationType;
import com.jobela.jobela_api.common.exception.BadRequestException;
import com.jobela.jobela_api.common.exception.CandidateNotFoundException;
import com.jobela.jobela_api.common.exception.CandidateWorkAuthorizationAlreadyExistsException;
import com.jobela.jobela_api.common.exception.CandidateWorkAuthorizationNotFoundException;
import com.jobela.jobela_api.common.mapper.StringMapperHelper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CandidateWorkAuthorizationServiceImpl implements CandidateWorkAuthorizationService {

    private final CandidateRepository candidateRepository;
    private final CandidateWorkAuthorizationRepository candidateWorkAuthorizationRepository;
    private final CandidateWorkAuthorizationMapper candidateWorkAuthorizationMapper;
    private final StringMapperHelper stringMapperHelper;

    @Override
    public CandidateWorkAuthorizationResponse createWorkAuthorization(
            Long candidateId, CreateCandidateWorkAuthorizationRequest request) {
        log.info("Creating work authorization for candidateId={}", candidateId);

        var candidate = getCandidateOrThrow(candidateId);

        var cleanedCountry = stringMapperHelper.clean(request.country());

        validateCountry(cleanedCountry);

        if (candidateWorkAuthorizationRepository.existsByCandidateIdAndCountryIgnoreCaseAndAuthorizationType(
                candidateId, cleanedCountry, request.authorizationType()
        )) {
            throw new CandidateWorkAuthorizationAlreadyExistsException("Work authorization already exists for country "
                    + cleanedCountry + " and authorization type " + request.authorizationType());
        }
        var workAuthorization = candidateWorkAuthorizationMapper.toEntity(request);

        workAuthorization.setCandidate(candidate);
        workAuthorization.setCountry(cleanedCountry);

        var savedWorkAuthorization = candidateWorkAuthorizationRepository.save(workAuthorization);

        log.info("Work authorization successfully created for candidateId={}", candidateId);

        return candidateWorkAuthorizationMapper.toResponse(savedWorkAuthorization);
    }

    @Override
    @Transactional(readOnly = true)
    public CandidateWorkAuthorizationResponse getWorkAuthorization(
            Long candidateId, Long candidateWorkAuthorizationId) {
        log.info("Fetching work authorization with id={} for candidateId={}", candidateWorkAuthorizationId, candidateId);

        getCandidateOrThrow(candidateId);

        var workAuthorization = getWorkAuthorizationOrThrow(candidateId, candidateWorkAuthorizationId);

        return candidateWorkAuthorizationMapper.toResponse(workAuthorization);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CandidateWorkAuthorizationResponse> getAllWorkAuthorizations(Long candidateId) {
        log.info("Fetching all work authorizations for candidateId={}", candidateId);

        getCandidateOrThrow(candidateId);

        var workAuthorizations = candidateWorkAuthorizationRepository.findAllByCandidateId(candidateId);

        return workAuthorizations.stream()
                .map(candidateWorkAuthorizationMapper::toResponse)
                .toList();
    }

    @Override
    public CandidateWorkAuthorizationResponse updateWorkAuthorization(
            Long candidateId, Long candidateWorkAuthorizationId, UpdateCandidateWorkAuthorizationRequest request) {
        log.info("Updating work authorization with id={} for candidateId={}", candidateWorkAuthorizationId, candidateId);

        var workAuthorization = getWorkAuthorizationOrThrow(candidateId, candidateWorkAuthorizationId);

        var finalCountry = request.country() != null
                ? stringMapperHelper.clean(request.country())
                : workAuthorization.getCountry();

        var finalAuthorizationType = request.authorizationType() != null
                ? request.authorizationType()
                : workAuthorization.getAuthorizationType();

        validateCountry(finalCountry);
        validateAuthorizationType(finalAuthorizationType);

        if (candidateWorkAuthorizationRepository.existsByCandidateIdAndCountryIgnoreCaseAndAuthorizationTypeAndIdNot(
                candidateId, finalCountry, finalAuthorizationType, workAuthorization.getId()
        )) {
            throw new CandidateWorkAuthorizationAlreadyExistsException("Work authorization already exists for country "
                    + finalCountry + " and authorization type " + finalAuthorizationType);
        }

        candidateWorkAuthorizationMapper.updateEntity(request, workAuthorization);
        workAuthorization.setCountry(finalCountry);

        var updatedWorkAuthorization = candidateWorkAuthorizationRepository.save(workAuthorization);

        log.info("Work authorization successfully updated with id={} for candidateId={}",
                candidateWorkAuthorizationId, candidateId);

        return candidateWorkAuthorizationMapper.toResponse(updatedWorkAuthorization);
    }

    @Override
    public void deleteWorkAuthorization(Long candidateId, Long candidateWorkAuthorizationId) {
        log.info("Deleting work authorization with id={} for candidateId={}", candidateWorkAuthorizationId, candidateId);

        getCandidateOrThrow(candidateId);

        var workAuthorization = getWorkAuthorizationOrThrow(candidateId, candidateWorkAuthorizationId);

        candidateWorkAuthorizationRepository.delete(workAuthorization);
    }



    private Candidate getCandidateOrThrow(Long candidateId) {
        return candidateRepository.findById(candidateId)
                .orElseThrow(() -> new CandidateNotFoundException(
                        "Candidate not found for candidateId " + candidateId));
    }

    private CandidateWorkAuthorization getWorkAuthorizationOrThrow(Long candidateId, Long candidateWorkAuthorizationId) {
        return candidateWorkAuthorizationRepository.findByIdAndCandidateId(candidateWorkAuthorizationId, candidateId)
                .orElseThrow(() -> new CandidateWorkAuthorizationNotFoundException("Work authorization with id "
                + candidateWorkAuthorizationId + " not found for candidateId " + candidateId));
    }

    private void validateCountry(String cleanedCountry) {
        if (cleanedCountry == null || cleanedCountry.isBlank()) {
            throw new BadRequestException("Country cannot be blank");
        }
    }

    private void validateAuthorizationType(AuthorizationType authorizationType) {
        if (authorizationType == null) {
            throw new BadRequestException("Authorization type cannot be null");
        }
    }
}
