package com.jobela.jobela_api.candidate.mapper;


import com.jobela.jobela_api.candidate.dto.request.education.CreateCandidateEducationRequest;
import com.jobela.jobela_api.candidate.dto.request.education.UpdateCandidateEducationRequest;
import com.jobela.jobela_api.candidate.dto.response.education.CandidateEducationResponse;
import com.jobela.jobela_api.candidate.entity.CandidateEducation;
import com.jobela.jobela_api.common.mapper.StringMapperHelper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = StringMapperHelper.class)
public interface CandidateEducationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "candidate", ignore = true)
    @Mapping(target = "schoolName", source = "schoolName", qualifiedByName = "clean")
    @Mapping(target = "fieldOfStudy", source = "fieldOfStudy", qualifiedByName = "clean")
    @Mapping(target = "degree", source = "degree", qualifiedByName = "clean")
    @Mapping(target = "city", source = "city", qualifiedByName = "clean")
    @Mapping(target = "country", source = "country", qualifiedByName = "clean")
    @Mapping(target = "description", source = "description", qualifiedByName = "clean")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    CandidateEducation toEntity(CreateCandidateEducationRequest request);

    @Mapping(target = "candidateId", source = "candidate.id")
    CandidateEducationResponse toResponse(CandidateEducation candidateEducation);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "candidate", ignore = true)
    @Mapping(target = "schoolName", source = "schoolName", qualifiedByName = "clean")
    @Mapping(target = "fieldOfStudy", source = "fieldOfStudy", qualifiedByName = "clean")
    @Mapping(target = "degree", source = "degree", qualifiedByName = "clean")
    @Mapping(target = "city", source = "city", qualifiedByName = "clean")
    @Mapping(target = "country", source = "country", qualifiedByName = "clean")
    @Mapping(target = "description", source = "description", qualifiedByName = "clean")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(UpdateCandidateEducationRequest request, @MappingTarget CandidateEducation candidateEducation);
}
