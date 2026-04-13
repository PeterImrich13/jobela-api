package com.jobela.jobela_api.candidate.repository;

import com.jobela.jobela_api.candidate.entity.CandidateDocument;
import com.jobela.jobela_api.common.enums.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CandidateDocumentRepository extends JpaRepository<CandidateDocument, Long> {
    Optional<CandidateDocument> findByIdAndCandidateId(Long candidateDocumentId, Long candidateId);
    List<CandidateDocument> findAllByCandidateId(Long candidateId);
    List<CandidateDocument> findAllByCandidateIdAndDocumentType(Long candidateId, DocumentType documentType);
    long countByCandidateIdAndDocumentType(Long candidateId, DocumentType documentType);
    long countByCandidateIdAndDocumentTypeAndIdNot(
            Long candidateId, DocumentType documentType, Long candidateDocumentId);
    boolean existsByCandidateIdAndFileUrl(Long candidateId, String fileUrl);
    boolean existsByCandidateIdAndFileUrlAndIdNot(Long candidateId, String fileUrl, Long candidateDocumentId);
}
