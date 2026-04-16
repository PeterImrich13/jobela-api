package com.jobela.jobela_api.candidate.mapper;


import com.jobela.jobela_api.candidate.dto.request.locationpreference.CreateCandidateLocationPreferenceRequest;
import com.jobela.jobela_api.candidate.dto.request.locationpreference.UpdateCandidateLocationPreferenceRequest;
import com.jobela.jobela_api.candidate.dto.response.locationpreference.CandidateLocationPreferenceResponse;
import com.jobela.jobela_api.candidate.entity.CandidateLocationPreference;
import com.jobela.jobela_api.common.mapper.StringMapperHelper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = StringMapperHelper.class)
public interface CandidateLocationPreferenceMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "candidate", ignore = true)
    @Mapping(target = "city", source = "city", qualifiedByName = "clean")
    @Mapping(target = "country", source = "country", qualifiedByName = "clean")
    @Mapping(target = "region", source = "region", qualifiedByName = "clean")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    CandidateLocationPreference toEntity(CreateCandidateLocationPreferenceRequest request);

    @Mapping(target = "candidateId", source = "candidate.id")
    CandidateLocationPreferenceResponse toResponse(CandidateLocationPreference candidateLocationPreference);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "candidate", ignore = true)
    @Mapping(target = "city", source = "city", qualifiedByName = "clean")
    @Mapping(target = "country", source = "country", qualifiedByName = "clean")
    @Mapping(target = "region", source = "region", qualifiedByName = "clean")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(UpdateCandidateLocationPreferenceRequest request,
                      @MappingTarget CandidateLocationPreference candidateLocationPreference);
}
