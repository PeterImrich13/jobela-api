package com.jobela.jobela_api.candidate.mapper;


import com.jobela.jobela_api.candidate.dto.request.candidate.CreateCandidateRequest;
import com.jobela.jobela_api.candidate.dto.request.candidate.UpdateCandidateRequest;
import com.jobela.jobela_api.candidate.dto.response.candidate.CandidateResponse;
import com.jobela.jobela_api.candidate.entity.Candidate;
import com.jobela.jobela_api.common.mapper.StringMapperHelper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = StringMapperHelper.class)
public interface CandidateMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "candidatePreference", ignore = true)
    @Mapping(target = "candidateWorkExperiences", ignore = true)
    @Mapping(target = "candidateEducations", ignore = true)
    @Mapping(target = "candidateCertifications", ignore = true)
    @Mapping(target = "candidateSkills", ignore = true)
    @Mapping(target = "candidateLanguages", ignore = true)
    @Mapping(target = "candidateDocuments", ignore = true)
    @Mapping(target = "candidateLocationPreferences", ignore = true)
    @Mapping(target = "candidateWorkAuthorizations", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)

    @Mapping(target = "titleBefore", source = "titleBefore", qualifiedByName = "clean")
    @Mapping(target = "titleAfter", source = "titleAfter", qualifiedByName = "clean")
    @Mapping(target = "firstName", source = "firstName", qualifiedByName = "clean")
    @Mapping(target = "lastName", source = "lastName", qualifiedByName = "clean")
    @Mapping(target = "phone", source = "phone", qualifiedByName = "clean")
    @Mapping(target = "city", source = "city", qualifiedByName = "clean")
    @Mapping(target = "country", source = "country", qualifiedByName = "clean")
    @Mapping(target = "nationality", source = "nationality", qualifiedByName = "clean")
    @Mapping(target = "headline", source = "headline", qualifiedByName = "clean")
    @Mapping(target = "summary", source = "summary", qualifiedByName = "clean")
    @Mapping(target = "profilePhotoUrl", source = "profilePhotoUrl", qualifiedByName = "clean")
    Candidate toEntity(CreateCandidateRequest request);

    @Mapping(target = "userId", source = "user.id")
    CandidateResponse toResponse(Candidate candidate);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "candidatePreference", ignore = true)
    @Mapping(target = "candidateWorkExperiences", ignore = true)
    @Mapping(target = "candidateEducations", ignore = true)
    @Mapping(target = "candidateCertifications", ignore = true)
    @Mapping(target = "candidateSkills", ignore = true)
    @Mapping(target = "candidateLanguages", ignore = true)
    @Mapping(target = "candidateDocuments", ignore = true)
    @Mapping(target = "candidateLocationPreferences", ignore = true)
    @Mapping(target = "candidateWorkAuthorizations", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)

    @Mapping(target = "titleBefore", source = "titleBefore", qualifiedByName = "clean")
    @Mapping(target = "titleAfter", source = "titleAfter", qualifiedByName = "clean")
    @Mapping(target = "firstName", source = "firstName", qualifiedByName = "clean")
    @Mapping(target = "lastName", source = "lastName", qualifiedByName = "clean")
    @Mapping(target = "phone", source = "phone", qualifiedByName = "clean")
    @Mapping(target = "city", source = "city", qualifiedByName = "clean")
    @Mapping(target = "country", source = "country", qualifiedByName = "clean")
    @Mapping(target = "nationality", source = "nationality", qualifiedByName = "clean")
    @Mapping(target = "headline", source = "headline", qualifiedByName = "clean")
    @Mapping(target = "summary", source = "summary", qualifiedByName = "clean")
    @Mapping(target = "profilePhotoUrl", source = "profilePhotoUrl", qualifiedByName = "clean")
    void updateCandidateFromRequest(UpdateCandidateRequest request, @MappingTarget Candidate candidate);

}

