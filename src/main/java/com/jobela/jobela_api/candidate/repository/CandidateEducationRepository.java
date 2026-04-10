package com.jobela.jobela_api.candidate.repository;

import com.jobela.jobela_api.candidate.entity.CandidateEducation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CandidateEducationRepository extends JpaRepository<CandidateEducation, Long> {
     Optional<CandidateEducation> findByIdAndCandidateId(Long candidateEducationId, Long candidateId);
     List<CandidateEducation> findAllByCandidateId(Long candidateId);
     boolean existsByCandidateIdAndSchoolNameIgnoreCaseAndDegreeIgnoreCase(Long candidateId, String schoolName, String degree);
     boolean existsByCandidateIdAndSchoolNameIgnoreCaseAndDegreeIgnoreCaseAndIdNot(Long candidateId, String schoolName, String degree, Long candidateEducationId);
}
