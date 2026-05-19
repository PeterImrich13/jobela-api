package com.jobela.jobela_api.candidate.service.profile;

import com.jobela.jobela_api.candidate.dto.request.profile.CandidatePublicProfileSearchCriteria;
import com.jobela.jobela_api.candidate.dto.response.profile.CandidatePublicProfileResponse;
import com.jobela.jobela_api.candidate.dto.response.profile.CandidatePublicProfileSummaryResponse;
import com.jobela.jobela_api.candidate.entity.Candidate;
import com.jobela.jobela_api.candidate.mapper.*;
import com.jobela.jobela_api.candidate.repository.CandidateRepository;
import com.jobela.jobela_api.candidate.specification.CandidateSpecification;
import com.jobela.jobela_api.common.exception.CandidateNotFoundException;
import com.jobela.jobela_api.common.pagination.PaginationUtils;
import com.jobela.jobela_api.common.sort.CandidatePublicProfileSortFields;
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
public class CandidatePublicProfileServiceImpl implements CandidatePublicProfileService {

    private final CandidateRepository candidateRepository;
    private final CandidatePreferenceMapper candidatePreferenceMapper;
    private final CandidateEducationMapper candidateEducationMapper;
    private final CandidateCertificationMapper candidateCertificationMapper;
    private final CandidateSkillMapper candidateSkillMapper;
    private final CandidateLanguageMapper candidateLanguageMapper;
    private final CandidateDocumentMapper candidateDocumentMapper;
    private final CandidateLocationPreferenceMapper candidateLocationPreferenceMapper;
    private final CandidateWorkAuthorizationMapper candidateWorkAuthorizationMapper;
    private final CandidateWorkExperienceMapper candidateWorkExperienceMapper;
    private final CandidatePublicProfileMapper candidatePublicProfileMapper;

    @Override
    public CandidatePublicProfileResponse getCandidatePublicProfile(Long candidateId) {
        log.info("Fetching public candidate profile for candidateId={}", candidateId);

        var candidate = getCandidateOrThrow(candidateId);

        if (!candidate.isProfileVisible()) {
            throw new CandidateNotFoundException("Candidate public profile not found");
        }

        return toPublicProfileResponse(candidate);
    }

    @Override
    public Page<CandidatePublicProfileSummaryResponse> getAllCandidatePublicProfiles(
            CandidatePublicProfileSearchCriteria criteria, Pageable pageable) {
        log.info("Fetching all visible public candidate profiles with criteria={} and pagination", criteria);

        PaginationUtils.validatePageable(
                pageable,
                CandidatePublicProfileSortFields.ALLOWED);

        var specification = CandidateSpecification.profileVisible()
                .and(CandidateSpecification.countryEquals(criteria.country()))
                .and(CandidateSpecification.cityEquals(criteria.city()))
                .and(CandidateSpecification.search(criteria.search()))
                .and(CandidateSpecification.skillEquals(criteria.skill()))
                .and(CandidateSpecification.languageEquals(criteria.language()))
                .and(CandidateSpecification.openToWorkEquals(criteria.openToWork()))
                .and(CandidateSpecification.targetPositionEquals(criteria.targetPosition()))
                .and(CandidateSpecification.languageLevelEquals(criteria.level()))
                .and(CandidateSpecification.authorizationTypeEquals(criteria.authorizationType()))
                .and(CandidateSpecification.sponsorshipRequiredEquals(criteria.sponsorshipRequired()));

        return candidateRepository.findAll(specification, pageable)
                .map(candidatePublicProfileMapper::toSummaryResponse);
    }

    private Candidate getCandidateOrThrow(Long candidateId) {
        return candidateRepository.findById(candidateId)
                .orElseThrow(() -> new CandidateNotFoundException("Candidate not found for candidateId: " + candidateId));
    }

    private CandidatePublicProfileResponse toPublicProfileResponse(Candidate candidate) {
        return new CandidatePublicProfileResponse(
                candidate.getTitleBefore(),
                candidate.getTitleAfter(),
                candidate.getFirstName(),
                candidate.getLastName(),
                candidate.getGender(),
                candidate.getPhone(),
                candidate.getCity(),
                candidate.getCountry(),
                candidate.getNationality(),
                candidate.getDateOfBirth(),
                candidate.getHeadline(),
                candidate.getSummary(),
                candidate.getProfilePhotoUrl(),

                candidate.getCandidatePreference() == null ? null
                        : candidatePreferenceMapper.toResponse(candidate.getCandidatePreference()),

                candidate.getCandidateWorkExperiences()
                        .stream()
                        .map(candidateWorkExperienceMapper::toResponse)
                        .toList(),

                candidate.getCandidateEducations()
                        .stream()
                        .map(candidateEducationMapper::toResponse)
                        .toList(),

                candidate.getCandidateCertifications()
                        .stream()
                        .map(candidateCertificationMapper::toResponse)
                        .toList(),

                candidate.getCandidateSkills()
                        .stream()
                        .map(candidateSkillMapper::toResponse)
                        .toList(),

                candidate.getCandidateLanguages()
                        .stream()
                        .map(candidateLanguageMapper::toResponse)
                        .toList(),

                candidate.getCandidateDocuments()
                        .stream()
                        .map(candidateDocumentMapper::toResponse)
                        .toList(),

                candidate.getCandidateLocationPreferences()
                        .stream()
                        .map(candidateLocationPreferenceMapper::toResponse)
                        .toList(),

                candidate.getCandidateWorkAuthorizations()
                        .stream()
                        .map(candidateWorkAuthorizationMapper::toResponse)
                        .toList()
        );
    }
}

