package com.jobela.jobela_api.candidate.service.document;

import com.jobela.jobela_api.candidate.dto.request.document.CreateCandidateDocumentRequest;
import com.jobela.jobela_api.candidate.dto.request.document.UpdateCandidateDocumentRequest;
import com.jobela.jobela_api.candidate.dto.response.document.CandidateDocumentResponse;
import com.jobela.jobela_api.candidate.entity.Candidate;
import com.jobela.jobela_api.candidate.entity.CandidateDocument;
import com.jobela.jobela_api.candidate.mapper.CandidateDocumentMapper;
import com.jobela.jobela_api.candidate.repository.CandidateDocumentRepository;
import com.jobela.jobela_api.candidate.repository.CandidateRepository;
import com.jobela.jobela_api.common.enums.DocumentType;
import com.jobela.jobela_api.common.exception.BadRequestException;
import com.jobela.jobela_api.common.exception.CandidateDocumentAlreadyExistsException;
import com.jobela.jobela_api.common.exception.CandidateDocumentNotFoundException;
import com.jobela.jobela_api.common.exception.CandidateNotFoundException;
import com.jobela.jobela_api.common.mapper.StringMapperHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CandidateDocumentServiceImpl implements CandidateDocumentService {

    private final CandidateRepository candidateRepository;
    private final CandidateDocumentRepository candidateDocumentRepository;
    private final CandidateDocumentMapper candidateDocumentMapper;
    private final StringMapperHelper stringMapperHelper;

    @Override
    public CandidateDocumentResponse createCandidateDocument(Long candidateId, CreateCandidateDocumentRequest request) {
        log.info("Creating candidate document for candidateId={}", candidateId);

        var candidate = getCandidateByIdOrThrow(candidateId);

        var cleanedFileName = stringMapperHelper.clean(request.fileName());
        var cleanedFileUrl = stringMapperHelper.clean(request.fileUrl());

        validateFileName(cleanedFileName);
        validateFileUrl(cleanedFileUrl);
        validateDocumentType(request.documentType());
        validateDocumentLimitForCreate(candidateId, request.documentType());

        if (candidateDocumentRepository.existsByCandidateIdAndFileUrl(candidateId, cleanedFileUrl)) {
            throw new CandidateDocumentAlreadyExistsException("Candidate document with file url " + cleanedFileUrl +
                    " already exists for candidateId: " + candidateId);
        }

        var document = candidateDocumentMapper.toEntity(request);
        document.setCandidate(candidate);

        validateRequiredFields(document);

        var savedDocument = candidateDocumentRepository.save(document);

        log.info("Document successfully created with id={} for candidateId={}", savedDocument.getId(), candidateId);

        return candidateDocumentMapper.toResponse(savedDocument);
    }

    @Override
    @Transactional(readOnly = true)
    public CandidateDocumentResponse getCandidateDocument(Long candidateId, Long candidateDocumentId) {
        log.info("Fetching candidate document with id={} for candidateId={}", candidateDocumentId, candidateId);

        getCandidateDocumentOrThrow(candidateId, candidateDocumentId);

        var document = getCandidateDocumentOrThrow(candidateId, candidateDocumentId);

        return candidateDocumentMapper.toResponse(document);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CandidateDocumentResponse> getCandidateDocuments(Long candidateId, DocumentType documentType) {
        log.info("Fetching all candidate documents for candidateId={} and documentType={}", candidateId, documentType);

        getCandidateByIdOrThrow(candidateId);

        List<CandidateDocument> documents;

        if (documentType != null) {
            documents = candidateDocumentRepository.findAllByCandidateIdAndDocumentType(candidateId, documentType);
        } else {
            documents = candidateDocumentRepository.findAllByCandidateId(candidateId);
        }

        return documents.stream()
                .map(candidateDocumentMapper::toResponse)
                .toList();
    }

    @Override
    public CandidateDocumentResponse updateCandidateDocument(
            Long candidateId, Long candidateDocumentId, UpdateCandidateDocumentRequest request) {
        log.info("Updating candidate document with id={} for candidateId={}", candidateDocumentId, candidateId);

        getCandidateByIdOrThrow(candidateId);
        var document = getCandidateDocumentOrThrow(candidateId, candidateDocumentId);

       var finalFileName = resolveFinalFileName(request, document);
       var finalFileUrl = resolveFinalFileUrl(request, document);
       var finalDocumentType = resolveFinalDocumentType(request, document);

        validateFileName(finalFileName);
        validateFileUrl(finalFileUrl);
        validateDocumentType(finalDocumentType);
        validateDocumentLimitForUpdate(candidateId, candidateDocumentId, finalDocumentType);

        if (candidateDocumentRepository.existsByCandidateIdAndFileUrlAndIdNot(
                candidateId, finalFileUrl, candidateDocumentId
        )) {
            throw new CandidateDocumentAlreadyExistsException("Candidate document with file url " + finalFileUrl +
                    " already exists for candidateId " + candidateId);
        }

        candidateDocumentMapper.updateEntity(request, document);

        validateRequiredFields(document);

        var updatedDocument = candidateDocumentRepository.save(document);

        log.info("Candidate document with id={} successfully updated for candidateId={}", candidateDocumentId, candidateId);

        return candidateDocumentMapper.toResponse(updatedDocument);
    }

    @Override
    public void deleteCandidateDocument(Long candidateId, Long candidateDocumentId) {
        log.info("Deleting candidate document with id={} for candidateId={}", candidateDocumentId, candidateId);

        var document = getCandidateDocumentOrThrow(candidateId, candidateDocumentId);

        candidateDocumentRepository.delete(document);

        log.info("Candidate document with id={} successfully deleted for candidateId={}", candidateDocumentId, candidateId);
    }

    private Candidate getCandidateByIdOrThrow(Long candidateId) {
        return candidateRepository.findById(candidateId)
                .orElseThrow(() -> new CandidateNotFoundException("Candidate with id " + candidateId + " not found"));
    }

    private CandidateDocument getCandidateDocumentOrThrow(Long candidateId, Long candidateDocumentId) {
        return candidateDocumentRepository.findByIdAndCandidateId(candidateDocumentId, candidateId)
                .orElseThrow(() -> new CandidateDocumentNotFoundException("Candidate document with id "
                        + candidateDocumentId + " for candidateId " + candidateId + " not found"));
    }

    private void validateRequiredFields(CandidateDocument candidateDocument) {
        validateDocumentType(candidateDocument.getDocumentType());
        validateFileName(candidateDocument.getFileName());
        validateFileUrl(candidateDocument.getFileUrl());
    }
    private void validateDocumentType(DocumentType documentType) {
        if (documentType == null) {
            throw new BadRequestException("Document type cannot be null");
        }
    }

    private void validateFileName(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            throw new BadRequestException("File name cannot be blank");
        }
    }

    private void validateFileUrl(String fileUrl) {
        if (fileUrl == null || fileUrl.isBlank()) {
            throw new BadRequestException("File url cannot be blank");
        }
    }

    private String resolveFinalFileName(UpdateCandidateDocumentRequest request, CandidateDocument candidateDocument) {
        if (request.fileName() == null) {
            return candidateDocument.getFileName();
        }

        var cleanedFileName = stringMapperHelper.clean(request.fileName());
        validateFileName(cleanedFileName);
        return cleanedFileName;
    }

    private String resolveFinalFileUrl(UpdateCandidateDocumentRequest request, CandidateDocument candidateDocument) {
        if (request.fileUrl() == null) {
            return candidateDocument.getFileUrl();
        }

        var cleanedFileUrl = stringMapperHelper.clean(request.fileUrl());
        validateFileUrl(cleanedFileUrl);
        return cleanedFileUrl;
    }

    private DocumentType resolveFinalDocumentType(UpdateCandidateDocumentRequest request, CandidateDocument candidateDocument) {
        var finalDocumentType = request.documentType() != null ? request.documentType() : candidateDocument.getDocumentType();

        validateDocumentType(finalDocumentType);
        return finalDocumentType;
    }

    private void validateDocumentLimitForCreate(Long candidateId, DocumentType documentType) {
        long documentCount = candidateDocumentRepository.countByCandidateIdAndDocumentType(candidateId, documentType);

        switch (documentType) {
            case CV, COVER_LETTER -> {
                if (documentCount >= 1) {
                    throw new BadRequestException(documentType + " already exists for candidateId: " + candidateId);
                }
            }
            case CERTIFICATE, DIPLOMA, PORTFOLIO, OTHER -> {
                if (documentCount >= 3) {
                    throw new BadRequestException("Maximum number of documents reached for type "
                    + documentType + " for candidate with id: " + candidateId);
                }
            }
        }
    }

    private void validateDocumentLimitForUpdate(
            Long candidateId, Long candidateDocumentId, DocumentType documentType) {
        long documentCount = candidateDocumentRepository.countByCandidateIdAndDocumentTypeAndIdNot(
                candidateId, documentType, candidateDocumentId
        );

        switch (documentType) {
            case CV, COVER_LETTER -> {
                if (documentCount >= 1) {
                    throw new BadRequestException(documentType + " already exists for candidateId " + candidateId);
                }
            }
            case CERTIFICATE, DIPLOMA, PORTFOLIO, OTHER -> {
                if (documentCount >= 3) {
                    throw new BadRequestException("Maximum number of documents reached for type " +
                            documentType + " for candidateId " + candidateId);
                }
            }
        }
    }
}
