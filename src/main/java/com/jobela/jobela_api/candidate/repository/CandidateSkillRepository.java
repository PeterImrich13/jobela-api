package com.jobela.jobela_api.candidate.repository;

import com.jobela.jobela_api.candidate.entity.CandidateSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CandidateSkillRepository extends JpaRepository<CandidateSkill, Long> {
    List<CandidateSkill> findAllByCandidateId(Long candidateId);
    Optional<CandidateSkill> findByIdAndCandidateId(Long skillId, Long candidateId);
    boolean existsByCandidateIdAndSkillNameIgnoreCase(Long candidateId, String skillName);
    boolean existsByCandidateIdAndSkillNameIgnoreCaseAndIdNot(Long candidateId, String skillName, Long skillId);

}
