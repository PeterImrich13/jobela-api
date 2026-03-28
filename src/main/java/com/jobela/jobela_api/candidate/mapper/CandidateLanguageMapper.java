package com.jobela.jobela_api.candidate.mapper;


import com.jobela.jobela_api.candidate.dto.request.language.CreateCandidateLanguageRequest;
import com.jobela.jobela_api.candidate.dto.request.language.UpdateCandidateLanguageRequest;
import com.jobela.jobela_api.candidate.dto.response.language.CandidateLanguageResponse;
import com.jobela.jobela_api.candidate.entity.CandidateLanguage;
import com.jobela.jobela_api.common.mapper.StringMapperHelper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = StringMapperHelper.class)
public interface CandidateLanguageMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "candidate", ignore = true)
    @Mapping(target = "languageName", source = "languageName", qualifiedByName = "clean")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    CandidateLanguage toEntity(CreateCandidateLanguageRequest request);

    @Mapping(target = "candidateId", source = "candidate.id")
    CandidateLanguageResponse toResponse(CandidateLanguage language);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "candidate", ignore = true)
    @Mapping(target = "languageName", source = "languageName", qualifiedByName = "clean")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(UpdateCandidateLanguageRequest request, @MappingTarget CandidateLanguage language);
}
