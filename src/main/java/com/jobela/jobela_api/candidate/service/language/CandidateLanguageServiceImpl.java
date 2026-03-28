package com.jobela.jobela_api.candidate.service.language;


import com.jobela.jobela_api.candidate.dto.request.language.CreateCandidateLanguageRequest;
import com.jobela.jobela_api.candidate.dto.request.language.UpdateCandidateLanguageRequest;
import com.jobela.jobela_api.candidate.dto.response.language.CandidateLanguageResponse;
import com.jobela.jobela_api.candidate.entity.Candidate;
import com.jobela.jobela_api.candidate.entity.CandidateLanguage;
import com.jobela.jobela_api.candidate.mapper.CandidateLanguageMapper;
import com.jobela.jobela_api.candidate.repository.CandidateLanguageRepository;
import com.jobela.jobela_api.candidate.repository.CandidateRepository;
import com.jobela.jobela_api.common.exception.BadRequestException;
import com.jobela.jobela_api.common.exception.CandidateLanguageAlreadyExistsException;
import com.jobela.jobela_api.common.exception.CandidateLanguageNotFoundException;
import com.jobela.jobela_api.common.exception.CandidateNotFoundException;
import com.jobela.jobela_api.common.mapper.StringMapperHelper;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CandidateLanguageServiceImpl implements CandidateLanguageService {

    private final CandidateLanguageRepository candidateLanguageRepository;
    private final CandidateLanguageMapper candidateLanguageMapper;
    private final CandidateRepository candidateRepository;
    private final StringMapperHelper stringMapperHelper;

    @Override
    public CandidateLanguageResponse createCandidateLanguage(Long candidateId, CreateCandidateLanguageRequest request) {
        log.info("Creating candidate language for candidate id={}", candidateId);

        var candidate = getCandidateByIdOrThrow(candidateId);
        var cleanedLanguageName = stringMapperHelper.clean(request.languageName());

        if (cleanedLanguageName == null || cleanedLanguageName.isEmpty()) {
            throw new BadRequestException("Language name cannot be blank");
        }

        if (candidateLanguageRepository.existsByCandidateIdAndLanguageNameIgnoreCase(candidateId, cleanedLanguageName)) {
            throw new CandidateLanguageAlreadyExistsException("Candidate language " + cleanedLanguageName +
                    " for candidateId " + candidateId + " already exists");
        }

        var language = candidateLanguageMapper.toEntity(request);
        language.setCandidate(candidate);

        validateRequiredFields(language);

        var savedLanguage = candidateLanguageRepository.save(language);

        log.info("Candidate language created successfully with id={} for candidateId={}", savedLanguage.getId(), candidateId);

        return candidateLanguageMapper.toResponse(savedLanguage);
    }

    @Override
    @Transactional(readOnly = true)
    public CandidateLanguageResponse getLanguageById(Long candidateId, Long candidateLanguageId) {
        log.info("Fetching language with id={} for candidateId={}", candidateLanguageId, candidateId);

        getCandidateByIdOrThrow(candidateId);

        var language = getLanguageByIdAndCandidateIdOrThrow(candidateLanguageId, candidateId);

        return candidateLanguageMapper.toResponse(language);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CandidateLanguageResponse> getAllLanguagesByCandidateId(Long candidateId) {
        log.info("Fetching all languages for candidateId={}", candidateId);

        getCandidateByIdOrThrow(candidateId);

        var languages = candidateLanguageRepository.findAllByCandidateId(candidateId);

        return languages.stream()
                .map(candidateLanguageMapper::toResponse)
                .toList();
    }

    @Override
    public CandidateLanguageResponse updateCandidateLanguage(Long candidateId, Long candidateLanguageId, UpdateCandidateLanguageRequest request) {
        log.info("Updating language with id={} for candidateId={}", candidateLanguageId, candidateId);

        getCandidateByIdOrThrow(candidateId);

        var language = getLanguageByIdAndCandidateIdOrThrow(candidateLanguageId, candidateId);

        String cleanedLanguageName = null;

        if (request.languageName() != null) {
            cleanedLanguageName = stringMapperHelper.clean(request.languageName());

            if (cleanedLanguageName == null || cleanedLanguageName.isEmpty()) {
                throw new BadRequestException("Language name cannot be blank");
            }

            if (candidateLanguageRepository.existsByCandidateIdAndLanguageNameIgnoreCaseAndIdNot(
                    candidateId, cleanedLanguageName, candidateLanguageId)) {
                throw new CandidateLanguageAlreadyExistsException("Language: " + cleanedLanguageName + " for candidateId: " +
                        candidateId + " already exists");
            }
        }
        candidateLanguageMapper.updateEntity(request, language);

       validateUpdatedFields(request, language);

        var updatedLanguage = candidateLanguageRepository.save(language);

        log.info("Candidate language updated successfully with id={} for candidateId={}", updatedLanguage.getId(), candidateId);

        return candidateLanguageMapper.toResponse(updatedLanguage);
    }

    @Override
    public void deleteLanguage(Long candidateId, Long candidateLanguageId) {
        log.info("Deleting language with id={} for candidateId={}", candidateLanguageId, candidateId);

        getCandidateByIdOrThrow(candidateId);

        var language = getLanguageByIdAndCandidateIdOrThrow(candidateLanguageId, candidateId);

        candidateLanguageRepository.delete(language);

        log.info("Language with id={} for candidateId={} successfully deleted", candidateLanguageId, candidateId);

    }

    private Candidate getCandidateByIdOrThrow(Long candidateId) {
        return candidateRepository.findById(candidateId)
                .orElseThrow(() -> new CandidateNotFoundException("Candidate not found with id: " + candidateId));
    }

    private CandidateLanguage getLanguageByIdAndCandidateIdOrThrow(Long candidateLanguageId, Long candidateId) {
        return candidateLanguageRepository.findByIdAndCandidateId(candidateLanguageId, candidateId)
                .orElseThrow(() -> new CandidateLanguageNotFoundException("Language with id: "
                + candidateLanguageId + " not found for candidateId " + candidateId));
    }

    private void validateRequiredFields(CandidateLanguage language) {
        if (language.getLanguageName() == null || language.getLanguageName().isBlank()) {
            throw new BadRequestException("Language name cannot be blank");
        }
    }

    private void validateUpdatedFields(UpdateCandidateLanguageRequest request, CandidateLanguage language) {
        if (request.languageName() != null
        && (language.getLanguageName() == null || language.getLanguageName().isBlank())) {
            throw new BadRequestException("Language name cannot be blank");
        }
    }
}
