package com.jobela.jobela_api.candidate.repository;

import com.jobela.jobela_api.candidate.entity.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    Optional<Candidate> findByUserId(Long userId);
    boolean existsByUserId(Long userId);
    Page<Candidate> findAllByProfileVisibleTrue(Pageable pageable);
}
