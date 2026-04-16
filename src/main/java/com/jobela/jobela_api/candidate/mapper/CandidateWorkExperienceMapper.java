package com.jobela.jobela_api.candidate.mapper;

import com.jobela.jobela_api.candidate.dto.request.workexperience.CreateCandidateWorkExperienceRequest;
import com.jobela.jobela_api.candidate.dto.request.workexperience.UpdateCandidateWorkExperienceRequest;
import com.jobela.jobela_api.candidate.dto.response.workexperience.CandidateWorkExperienceResponse;
import com.jobela.jobela_api.candidate.entity.CandidateWorkExperience;
import com.jobela.jobela_api.common.mapper.StringMapperHelper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = StringMapperHelper.class)
public interface CandidateWorkExperienceMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "candidate", ignore = true)
    @Mapping(target = "companyName", source = "companyName", qualifiedByName = "clean")
    @Mapping(target = "jobTitle", source = "jobTitle", qualifiedByName = "clean")
    @Mapping(target = "city", source = "city", qualifiedByName = "clean")
    @Mapping(target = "country", source = "country", qualifiedByName = "clean")
    @Mapping(target = "description", source = "description", qualifiedByName = "clean")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    CandidateWorkExperience toEntity(CreateCandidateWorkExperienceRequest request);

    @Mapping(target = "candidateId", source = "candidate.id")
    CandidateWorkExperienceResponse toResponse(CandidateWorkExperience candidateWorkExperience);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "candidate", ignore = true)
    @Mapping(target = "companyName", source = "companyName", qualifiedByName = "clean")
    @Mapping(target = "jobTitle", source = "jobTitle", qualifiedByName = "clean")
    @Mapping(target = "city", source = "city", qualifiedByName = "clean")
    @Mapping(target = "country", source = "country", qualifiedByName = "clean")
    @Mapping(target = "description", source = "description", qualifiedByName = "clean")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(UpdateCandidateWorkExperienceRequest request, @MappingTarget CandidateWorkExperience candidateWorkExperience);
}
