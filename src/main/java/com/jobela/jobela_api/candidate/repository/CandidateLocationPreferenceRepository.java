package com.jobela.jobela_api.candidate.repository;

import com.jobela.jobela_api.candidate.entity.CandidateLocationPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CandidateLocationPreferenceRepository extends JpaRepository<CandidateLocationPreference, Long> {
    Optional<CandidateLocationPreference> findByIdAndCandidateId(Long candidateLocationPreferenceId, Long candidateId);
    List<CandidateLocationPreference> findAllByCandidateId(Long candidateId);
}
