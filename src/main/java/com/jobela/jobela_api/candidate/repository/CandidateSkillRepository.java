package com.jobela.jobela_api.candidate.repository;

import com.jobela.jobela_api.candidate.entity.CandidateSkill;
import com.jobela.jobela_api.common.enums.SkillType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CandidateSkillRepository extends JpaRepository<CandidateSkill, Long> {
    List<CandidateSkill> findAllByCandidateId(Long candidateId);
    List<CandidateSkill> findAllByCandidateIdAndSkillType(Long candidateId, SkillType skillType);
    Optional<CandidateSkill> findByIdAndCandidateId(Long skillId, Long candidateId);
    boolean existsByCandidateIdAndSkillNameIgnoreCaseAndSkillType(
            Long candidateId, String skillName, SkillType skillType);
    boolean existsByCandidateIdAndSkillNameIgnoreCaseAndSkillTypeAndIdNot(
            Long candidateId, String skillName,SkillType skillType, Long skillId);

}
