package com.jobela.jobela_api.candidate.mapper;


import com.jobela.jobela_api.candidate.dto.request.candidate.CreateCandidateRequest;
import com.jobela.jobela_api.candidate.dto.request.candidate.UpdateCandidateRequest;
import com.jobela.jobela_api.candidate.dto.response.candidate.CandidateResponse;
import com.jobela.jobela_api.candidate.entity.Candidate;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CandidateMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "preference", ignore = true)
    @Mapping(target = "workExperiences", ignore = true)
    @Mapping(target = "educations", ignore = true)
    @Mapping(target = "certifications", ignore = true)
    @Mapping(target = "candidateSkills", ignore = true)
    @Mapping(target = "candidateLanguages", ignore = true)
    @Mapping(target = "candidateDocuments", ignore = true)
    @Mapping(target = "candidateLocationPreferences", ignore = true)
    @Mapping(target = "candidateWorkAuthorizations", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Candidate toEntity(CreateCandidateRequest request);

    @Mapping(target = "userId", source = "user.id")
    CandidateResponse toResponse(Candidate candidate);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "preference", ignore = true)
    @Mapping(target = "workExperiences", ignore = true)
    @Mapping(target = "educations", ignore = true)
    @Mapping(target = "certifications", ignore = true)
    @Mapping(target = "candidateSkills", ignore = true)
    @Mapping(target = "candidateLanguages", ignore = true)
    @Mapping(target = "candidateDocuments", ignore = true)
    @Mapping(target = "candidateLocationPreferences", ignore = true)
    @Mapping(target = "candidateWorkAuthorizations", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateCandidateFromRequest(UpdateCandidateRequest request, @MappingTarget Candidate candidate);





}

