package com.jobela.jobela_api.candidate.mapper;


import com.jobela.jobela_api.candidate.dto.request.skill.CreateCandidateSkillRequest;
import com.jobela.jobela_api.candidate.dto.request.skill.UpdateCandidateSkillRequest;
import com.jobela.jobela_api.candidate.dto.response.skill.CandidateSkillResponse;
import com.jobela.jobela_api.candidate.entity.CandidateSkill;
import com.jobela.jobela_api.common.mapper.StringMapperHelper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = StringMapperHelper.class)
public interface CandidateSkillMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "candidate", ignore = true)
    @Mapping(target = "skillName", source = "skillName", qualifiedByName = "clean")
    @Mapping(target = "createdAt", ignore = true)
    CandidateSkill toEntity(CreateCandidateSkillRequest request);

    @Mapping(target = "candidateId", source = "candidate.id")
    CandidateSkillResponse toResponse(CandidateSkill skill);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "candidate", ignore = true)
    @Mapping(target = "skillName", source = "skillName", qualifiedByName = "clean")
    @Mapping(target = "createdAt", ignore = true)
    void updateEntity(UpdateCandidateSkillRequest request, @MappingTarget CandidateSkill skill);
}
