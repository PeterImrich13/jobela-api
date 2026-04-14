package com.jobela.jobela_api.candidate.service.preference;


import com.jobela.jobela_api.candidate.dto.request.preference.CreateCandidatePreferenceRequest;
import com.jobela.jobela_api.candidate.dto.request.preference.UpdateCandidatePreferenceRequest;
import com.jobela.jobela_api.candidate.dto.response.preference.CandidatePreferenceResponse;
import com.jobela.jobela_api.candidate.entity.Candidate;
import com.jobela.jobela_api.candidate.entity.CandidatePreference;
import com.jobela.jobela_api.candidate.mapper.CandidatePreferenceMapper;
import com.jobela.jobela_api.candidate.repository.CandidatePreferenceRepository;
import com.jobela.jobela_api.candidate.repository.CandidateRepository;
import com.jobela.jobela_api.common.exception.BadRequestException;
import com.jobela.jobela_api.common.exception.CandidateNotFoundException;
import com.jobela.jobela_api.common.exception.CandidatePreferenceAlreadyExistsException;
import com.jobela.jobela_api.common.exception.CandidatePreferenceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CandidatePreferenceServiceImpl implements CandidatePreferenceService{

    private final CandidateRepository candidateRepository;
    private final CandidatePreferenceRepository candidatePreferenceRepository;
    private final CandidatePreferenceMapper candidatePreferenceMapper;

    @Override
    public CandidatePreferenceResponse createCandidatePreference(Long candidateId, CreateCandidatePreferenceRequest request) {
        log.info("Creating candidate preference for candidateId={}", candidateId);

        var candidate = getCandidateOrThrow(candidateId);

        validateExistingPreference(candidateId);
        validateSalaryCurrency(request.salaryCurrency());
        validateSalaryRange(request.desiredSalaryMin(), request.desiredSalaryMax());

        var preference = candidatePreferenceMapper.toEntity(request);

        preference.setCandidate(candidate);

        var savedPreference = candidatePreferenceRepository.save(preference);

        log.info("Candidate preference successfully created for candidateId={}", candidateId);

        return candidatePreferenceMapper.toResponse(savedPreference);
    }

    @Override
    @Transactional(readOnly = true)
    public CandidatePreferenceResponse getCandidatePreference(Long candidateId, Long candidatePreferenceId) {
        log.info("Fetching candidate preference with id={} for candidateId={}", candidatePreferenceId, candidateId);

        var preference = getCandidatePreferenceOrThrow(candidateId, candidatePreferenceId);

        return candidatePreferenceMapper.toResponse(preference);
    }

    @Override
    public CandidatePreferenceResponse updateCandidatePreference(
            Long candidateId, Long candidatePreferenceId, UpdateCandidatePreferenceRequest request) {
        log.info("Updating candidate preference with id={} for candidateId={}", candidatePreferenceId, candidateId);

        var preference = getCandidatePreferenceOrThrow(candidateId, candidatePreferenceId);

        var finalDesiredSalaryMin = request.desiredSalaryMin() != null
                ? request.desiredSalaryMin() : preference.getDesiredSalaryMin();

        var finalDesiredSalaryMax = request.desiredSalaryMax() != null
                ? request.desiredSalaryMax() : preference.getDesiredSalaryMax();

        var finalSalaryCurrency = request.salaryCurrency() != null
                ? request.salaryCurrency() : preference.getSalaryCurrency();

        validateSalaryRange(finalDesiredSalaryMin, finalDesiredSalaryMax);
        validateSalaryCurrency(finalSalaryCurrency);

        candidatePreferenceMapper.updateEntity(request, preference);

        var updatedPreference = candidatePreferenceRepository.save(preference);

        log.info("Candidate preference successfully updated with id={} for candidateId={}",
                candidatePreferenceId ,candidateId);

        return candidatePreferenceMapper.toResponse(updatedPreference);
    }

    @Override
    public void deleteCandidatePreference(Long candidateId, Long candidatePreferenceId) {
        log.info("Deleting candidate preference with id={} for candidateId={}", candidatePreferenceId, candidateId);

        var preference = getCandidatePreferenceOrThrow(candidateId, candidatePreferenceId);

        candidatePreferenceRepository.delete(preference);

        log.info("Candidate preference with id={} for candidateId={} successfully deleted",
                candidatePreferenceId, candidateId);
    }


    private Candidate getCandidateOrThrow(Long candidateId) {
        return candidateRepository.findById(candidateId)
                .orElseThrow(() -> new CandidateNotFoundException("Candidate not found with id " + candidateId));
    }

    private CandidatePreference getCandidatePreferenceOrThrow(Long candidateId, Long candidatePreferenceId) {
        return candidatePreferenceRepository.findByIdAndCandidateId(candidatePreferenceId, candidateId)
                .orElseThrow(() -> new CandidatePreferenceNotFoundException("Preference with id " +
                        candidatePreferenceId + " not found for candidateId " + candidateId));
    }

    private void validateExistingPreference(Long candidateId) {
        if (candidatePreferenceRepository.findByCandidateId(candidateId).isPresent()) {
            throw new CandidatePreferenceAlreadyExistsException("Preference already exists for candidateId "
                    + candidateId);
        }
    }

    private void validateSalaryCurrency(String salaryCurrency) {
        if (salaryCurrency != null && salaryCurrency.isBlank()) {
            throw new BadRequestException("Salary currency cannot be blank");
        }
    }

    private void validateSalaryRange(Integer desiredSalaryMin, Integer desiredSalaryMax) {
        if (desiredSalaryMin != null && desiredSalaryMax != null && desiredSalaryMax < desiredSalaryMin) {
            throw new BadRequestException("Desired salary min cannot be greater than desired salary max");
        }
    }
}
