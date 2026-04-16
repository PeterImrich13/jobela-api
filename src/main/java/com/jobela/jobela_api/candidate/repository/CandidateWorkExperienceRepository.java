package com.jobela.jobela_api.candidate.repository;

import com.jobela.jobela_api.candidate.entity.CandidateWorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CandidateWorkExperienceRepository extends JpaRepository<CandidateWorkExperience, Long> {
    Optional<CandidateWorkExperience> findByIdAndCandidateId(Long candidateWorkExperienceId, Long candidateId);
    List<CandidateWorkExperience> findAllByCandidateId(Long candidateId);
    boolean existsByCandidateIdAndCompanyNameIgnoreCaseAndJobTitleIgnoreCaseAndStartDate(
            Long candidateId, String companyName, String jobTitle, LocalDate startDate);
    boolean existsByCandidateIdAndCompanyNameIgnoreCaseAndJobTitleIgnoreCaseAndStartDateAndIdNot(
            Long candidateId, String companyName, String jobTitle, LocalDate startDate, Long candidateWorkExperienceId);
}
