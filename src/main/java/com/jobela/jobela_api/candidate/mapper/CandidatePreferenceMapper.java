package com.jobela.jobela_api.candidate.mapper;

import com.jobela.jobela_api.candidate.dto.request.preference.CreateCandidatePreferenceRequest;
import com.jobela.jobela_api.candidate.dto.request.preference.UpdateCandidatePreferenceRequest;
import com.jobela.jobela_api.candidate.dto.response.preference.CandidatePreferenceResponse;
import com.jobela.jobela_api.candidate.entity.CandidatePreference;
import com.jobela.jobela_api.common.mapper.StringMapperHelper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = StringMapperHelper.class)
public interface CandidatePreferenceMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "candidate", ignore = true)
    @Mapping(target = "salaryCurrency", source = "salaryCurrency", qualifiedByName = "clean")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    CandidatePreference toEntity(CreateCandidatePreferenceRequest request);

    @Mapping(target = "candidateId", source = "candidate.id")
    CandidatePreferenceResponse toResponse(CandidatePreference candidatePreference);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "candidate", ignore = true)
    @Mapping(target = "salaryCurrency", source = "salaryCurrency", qualifiedByName = "clean")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(UpdateCandidatePreferenceRequest request, @MappingTarget CandidatePreference candidatePreference);
}
