package com.jobela.jobela_api.candidate.repository;

import com.jobela.jobela_api.candidate.entity.CandidateLanguage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface CandidateLanguageRepository extends JpaRepository<CandidateLanguage, Long> {
    Optional<CandidateLanguage> findByIdAndCandidateId(Long id, Long candidateId);
    List<CandidateLanguage> findAllByCandidateId(Long candidateId);
    boolean existsByCandidateIdAndLanguageNameIgnoreCase(Long candidateId, String languageName);
    boolean existsByCandidateIdAndLanguageNameIgnoreCaseAndIdNot(Long candidateId, String languageName, Long candidateLanguageId);
}
