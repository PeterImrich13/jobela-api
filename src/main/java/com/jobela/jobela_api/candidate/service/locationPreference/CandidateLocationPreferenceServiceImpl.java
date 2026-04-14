package com.jobela.jobela_api.candidate.service.locationPreference;


import com.jobela.jobela_api.candidate.dto.request.locationPreference.CreateCandidateLocationPreferenceRequest;
import com.jobela.jobela_api.candidate.dto.request.locationPreference.UpdateCandidateLocationPreferenceRequest;
import com.jobela.jobela_api.candidate.dto.response.locationPreference.CandidateLocationPreferenceResponse;
import com.jobela.jobela_api.candidate.entity.Candidate;
import com.jobela.jobela_api.candidate.entity.CandidateLocationPreference;
import com.jobela.jobela_api.candidate.mapper.CandidateLocationPreferenceMapper;
import com.jobela.jobela_api.candidate.repository.CandidateLocationPreferenceRepository;
import com.jobela.jobela_api.candidate.repository.CandidateRepository;
import com.jobela.jobela_api.common.exception.BadRequestException;
import com.jobela.jobela_api.common.exception.CandidateLocationPreferenceNotFoundException;
import com.jobela.jobela_api.common.exception.CandidateNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CandidateLocationPreferenceServiceImpl implements CandidateLocationPreferenceService {

    private final CandidateRepository candidateRepository;
    private final CandidateLocationPreferenceRepository candidateLocationPreferenceRepository;
    private final CandidateLocationPreferenceMapper candidateLocationPreferenceMapper;


    @Override
    public CandidateLocationPreferenceResponse createLocationPreference(
            Long candidateId, CreateCandidateLocationPreferenceRequest request) {
        log.info("Creating location preference for candidateId={}", candidateId);

        var candidate = getCandidateByIdOrThrow(candidateId);

        var locationPreference = candidateLocationPreferenceMapper.toEntity(request);

        validateLocationPreference(locationPreference.getCity(),
                locationPreference.getCountry(),
                locationPreference.getRegion());

        locationPreference.setCandidate(candidate);

        var savedLocationPreference = candidateLocationPreferenceRepository.save(locationPreference);

        return candidateLocationPreferenceMapper.toResponse(savedLocationPreference);

    }

    @Override
    @Transactional(readOnly = true)
    public CandidateLocationPreferenceResponse getLocationPreference(
            Long candidateId, Long candidateLocationPreferenceId) {
        log.info("Fetching location preference with id={} for candidateId={}", candidateLocationPreferenceId, candidateId);

        var locationPreference = getLocationPreferenceOrThrow(candidateId, candidateLocationPreferenceId);

        return candidateLocationPreferenceMapper.toResponse(locationPreference);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CandidateLocationPreferenceResponse> getAllLocationPreferences(Long candidateId) {
        log.info("Fetching all location preferences for candidateId={}", candidateId);

        getCandidateByIdOrThrow(candidateId);

        var locationPreferences = candidateLocationPreferenceRepository.findAllByCandidateId(candidateId);

        return locationPreferences.stream()
                .map(candidateLocationPreferenceMapper::toResponse)
                .toList();
    }

    @Override
    public CandidateLocationPreferenceResponse updateLocationPreference(
            Long candidateId, Long candidateLocationPreferenceId, UpdateCandidateLocationPreferenceRequest request) {
        log.info("Updating location preference with id={} for candidateId={}", candidateLocationPreferenceId, candidateId);

        var locationPreference = getLocationPreferenceOrThrow(candidateId, candidateLocationPreferenceId);

        var finalCity = request.city() != null
                ? request.city().strip()
                : locationPreference.getCity();

        var finalCountry = request.country() != null
                ? request.country().strip()
                : locationPreference.getCountry();

        var finalRegion = request.region() != null
                ? request.region().strip()
                : locationPreference.getRegion();

        validateLocationPreference(finalCity, finalCountry, finalRegion);

        candidateLocationPreferenceMapper.updateEntity(request, locationPreference);

        var updatedLocationPreference = candidateLocationPreferenceRepository.save(locationPreference);

        log.info("Location preference with id={} successfully updated for candidateId={}",
                candidateLocationPreferenceId, candidateId);

        return candidateLocationPreferenceMapper.toResponse(updatedLocationPreference);
    }

    @Override
    public void deleteLocationPreference(Long candidateId, Long candidateLocationPreferenceId) {
        log.info("Deleting location preference with id={} for candidateId={}", candidateLocationPreferenceId, candidateId);

        var locationPreference = getLocationPreferenceOrThrow(candidateId, candidateLocationPreferenceId);

        candidateLocationPreferenceRepository.delete(locationPreference);

        log.info("Location preference with id={} successfully deleted for candidateId={}", candidateLocationPreferenceId, candidateId);
    }

    private Candidate getCandidateByIdOrThrow(Long candidateId) {
        return candidateRepository.findById(candidateId)
                .orElseThrow(() -> new CandidateNotFoundException("Candidate with id " + candidateId +
                        " not found"));
    }

    private CandidateLocationPreference getLocationPreferenceOrThrow(
            Long candidateId, Long candidateLocationPreferenceId) {
        return candidateLocationPreferenceRepository.findByIdAndCandidateId(candidateLocationPreferenceId, candidateId)
                .orElseThrow(() -> new CandidateLocationPreferenceNotFoundException("Candidate location preference with id "
                        + candidateLocationPreferenceId + " not found for candidateId " + candidateId));
    }

    private void validateLocationPreference(String city, String country, String region) {
        if (areAllLocationFieldsBlank(city, country, region)) {
            throw new BadRequestException(
                    "At least one of city, country or region must be provided"
            );
        }
    }

    private boolean areAllLocationFieldsBlank(String city, String country, String region) {
        return isBlank(city) && isBlank(country) && isBlank(region);
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
