package com.jobela.jobela_api.candidate.repository;

import com.jobela.jobela_api.candidate.entity.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CandidateRepository extends JpaRepository<Candidate, Long>, JpaSpecificationExecutor<Candidate> {

    Optional<Candidate> findByUserId(Long userId);
    boolean existsByUserId(Long userId);

    @Override
    @EntityGraph(attributePaths = "candidatePreference")
    Page<Candidate> findAll(Specification<Candidate> specification, Pageable pageable);
}
