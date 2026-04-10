package com.jobela.jobela_api.candidate.repository;

import com.jobela.jobela_api.candidate.entity.CandidateCertification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CandidateCertificationRepository extends JpaRepository<CandidateCertification, Long> {
    Optional<CandidateCertification> findByIdAndCandidateId(Long candidateCertificationId, Long candidateId);
    List<CandidateCertification> findAllByCandidateId(Long candidateId);
    boolean existsByCandidateIdAndNameIgnoreCaseAndIssuerIgnoreCase(
            Long candidateId, String name, String issuer);
    boolean existsByCandidateIdAndNameIgnoreCaseAndIssuerIgnoreCaseAndIdNot(
            Long candidateId, String name, String issuer, Long candidateCertificationId);
}
