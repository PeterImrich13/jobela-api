package com.jobela.jobela_api.candidate.mapper;


import com.jobela.jobela_api.candidate.dto.request.skill.CreateCandidateSkillRequest;
import com.jobela.jobela_api.candidate.dto.request.skill.UpdateCandidateSkillRequest;
import com.jobela.jobela_api.candidate.dto.response.skill.CandidateSkillResponse;
import com.jobela.jobela_api.candidate.entity.CandidateSkill;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CandidateSkillMapper {

    CandidateSkill toEntity(CreateCandidateSkillRequest request);

    @Mapping(target = "candidateId", source = "candidate.id")
    CandidateSkillResponse toResponse(CandidateSkill skill);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UpdateCandidateSkillRequest request, @MappingTarget CandidateSkill skill);
}
