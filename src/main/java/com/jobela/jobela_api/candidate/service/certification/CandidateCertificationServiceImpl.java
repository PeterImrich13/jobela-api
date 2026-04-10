package com.jobela.jobela_api.candidate.service.certification;

import com.jobela.jobela_api.candidate.dto.request.certification.CreateCandidateCertificationRequest;
import com.jobela.jobela_api.candidate.dto.request.certification.UpdateCandidateCertificationRequest;
import com.jobela.jobela_api.candidate.dto.response.certification.CandidateCertificationResponse;
import com.jobela.jobela_api.candidate.entity.Candidate;
import com.jobela.jobela_api.candidate.entity.CandidateCertification;
import com.jobela.jobela_api.candidate.mapper.CandidateCertificationMapper;
import com.jobela.jobela_api.candidate.repository.CandidateCertificationRepository;
import com.jobela.jobela_api.candidate.repository.CandidateRepository;
import com.jobela.jobela_api.common.exception.BadRequestException;
import com.jobela.jobela_api.common.exception.CandidateCertificationAlreadyExistsException;
import com.jobela.jobela_api.common.exception.CandidateCertificationNotFoundException;
import com.jobela.jobela_api.common.exception.CandidateNotFoundException;
import com.jobela.jobela_api.common.mapper.StringMapperHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CandidateCertificationServiceImpl implements CandidateCertificationService {

    private final CandidateCertificationRepository candidateCertificationRepository;
    private final CandidateCertificationMapper candidateCertificationMapper;
    private final CandidateRepository candidateRepository;
    private final StringMapperHelper stringMapperHelper;


    @Override
    public CandidateCertificationResponse createCandidateCertification(Long candidateId, CreateCandidateCertificationRequest request) {
        log.info("Creating candidate certification for candidateId={}", candidateId);

        var candidate = getCandidateByIdOrThrow(candidateId);

        var cleanedName = stringMapperHelper.clean(request.name());
        var cleanedIssuer = stringMapperHelper.clean(request.issuer());

        if (cleanedName == null || cleanedName.isBlank()) {
            throw new BadRequestException("Certification name cannot be blank");
        }
        if (cleanedIssuer == null || cleanedIssuer.isBlank()) {
            throw new BadRequestException("Certification issuer cannot be blank");
        }

        if (candidateCertificationRepository.existsByCandidateIdAndNameIgnoreCaseAndIssuerIgnoreCase(
                candidateId, cleanedName, cleanedIssuer
        )) {
            throw new CandidateCertificationAlreadyExistsException("Candidate certification with name: " + cleanedName +
                    " and issuer: " + cleanedIssuer + " for candidateId: " + candidateId + " already exists");
        }

        var certification = candidateCertificationMapper.toEntity(request);
        certification.setCandidate(candidate);

        validateRequiredFields(certification);

        var savedCertification = candidateCertificationRepository.save(certification);

        log.info("Candidate certification created successfully with id={} for candidateId={}",savedCertification.getId(), candidateId);

        return candidateCertificationMapper.toResponse(savedCertification);
    }

    @Override
    @Transactional(readOnly = true)
    public CandidateCertificationResponse getCandidateCertificationById(Long candidateId, Long candidateCertificationId) {
        log.info("Fetching candidate certification with id={} for candidateId={}", candidateCertificationId, candidateId);

        var certification = getCandidateCertificationByIdOrThrow(candidateId, candidateCertificationId);

        return candidateCertificationMapper.toResponse(certification);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CandidateCertificationResponse> getAllCandidateCertifications(Long candidateId) {
        log.info("Fetching all certifications for candidateId={}", candidateId);

        getCandidateByIdOrThrow(candidateId);

        var certifications = candidateCertificationRepository.findAllByCandidateId(candidateId);

        return certifications.stream()
                .map(candidateCertificationMapper::toResponse)
                .toList();
    }

    @Override
    public CandidateCertificationResponse updateCandidateCertification(
            Long candidateId, Long candidateCertificationId, UpdateCandidateCertificationRequest request) {
        log.info("Updating candidate certification with id={} for candidateId ={}",candidateCertificationId, candidateId);

        var certification = getCandidateCertificationByIdOrThrow(candidateId, candidateCertificationId);

        var cleanedName = stringMapperHelper.clean(request.name());
        var cleanedIssuer = stringMapperHelper.clean(request.issuer());

        var finalName = cleanedName != null ? cleanedName : certification.getName();
        var finalIssuer = cleanedIssuer != null ? cleanedIssuer : certification.getIssuer();

        if (finalName == null || finalName.isBlank()) {
            throw new BadRequestException("Certification name cannot be blank");
        }

        if (finalIssuer == null || finalIssuer.isBlank()) {
            throw new BadRequestException("Certification issuer cannot be blank");
        }

        if (candidateCertificationRepository.existsByCandidateIdAndNameIgnoreCaseAndIssuerIgnoreCaseAndIdNot(candidateId, finalName, finalIssuer, candidateCertificationId)) {
            throw new CandidateCertificationAlreadyExistsException("Candidate certification with id " + candidateCertificationId +
                    " and name " + finalName + " and issuer " + finalIssuer + " for candidateId " + candidateId +
                    " already exists");
        }

        candidateCertificationMapper.updateEntity(request, certification);

        validateRequiredFields(certification);

        var updatedCertification = candidateCertificationRepository.save(certification);

        log.info("Candidate certification updated successfully with id={} for candidateId={}", candidateCertificationId, candidateId);

        return candidateCertificationMapper.toResponse(updatedCertification);
    }

    @Override
    public void deleteCandidateCertification(Long candidateId, Long candidateCertificationId) {
        log.info("Deleting candidate certification with id={} for candidateId={}", candidateCertificationId, candidateId);

        var certification = getCandidateCertificationByIdOrThrow(candidateId, candidateCertificationId);

        candidateCertificationRepository.delete(certification);

        log.info("Candidate certification with id={} for candidateId={} deleted successfully", candidateCertificationId, candidateId);
    }

    private Candidate getCandidateByIdOrThrow(Long candidateId) {
        return candidateRepository.findById(candidateId)
                .orElseThrow(() -> new CandidateNotFoundException("Candidate with id " + candidateId + " not found."));
    }
    private CandidateCertification getCandidateCertificationByIdOrThrow(Long candidateId, Long candidateCertificationId) {
        return candidateCertificationRepository.findByIdAndCandidateId(candidateCertificationId, candidateId)
                .orElseThrow(() -> new CandidateCertificationNotFoundException("Candidate certification with id " + candidateCertificationId +
                        " not found for candidateId " + candidateId));
    }

    private void validateRequiredFields(CandidateCertification candidateCertification) {
        if (candidateCertification.getName() == null || candidateCertification.getName().isBlank()) {
            throw new BadRequestException("Certification name cannot be blank");
        }
        if (candidateCertification.getIssuer() == null || candidateCertification.getIssuer().isBlank()) {
            throw new BadRequestException("Certification issuer cannot be blank");
        }
    }
}
