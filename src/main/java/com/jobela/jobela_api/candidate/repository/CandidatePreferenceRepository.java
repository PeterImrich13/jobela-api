package com.jobela.jobela_api.candidate.repository;

import com.jobela.jobela_api.candidate.entity.CandidatePreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandidatePreferenceRepository extends JpaRepository<CandidatePreference, Long> {
    Optional<CandidatePreference> findByIdAndCandidateId(Long candidatePreferenceId, Long candidateId);
    Optional<CandidatePreference> findByCandidateId(Long candidateId);
}
