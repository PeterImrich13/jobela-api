package com.jobela.jobela_api.candidate.mapper;

import com.jobela.jobela_api.candidate.dto.request.workauthorization.CreateCandidateWorkAuthorizationRequest;
import com.jobela.jobela_api.candidate.dto.request.workauthorization.UpdateCandidateWorkAuthorizationRequest;
import com.jobela.jobela_api.candidate.dto.response.workauthorization.CandidateWorkAuthorizationResponse;
import com.jobela.jobela_api.candidate.entity.CandidateWorkAuthorization;
import com.jobela.jobela_api.common.mapper.StringMapperHelper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = StringMapperHelper.class)
public interface CandidateWorkAuthorizationMapper {

        @Mapping(target = "id", ignore = true)
        @Mapping(target = "candidate", ignore = true)
        @Mapping(target = "country", source = "country", qualifiedByName = "clean")
        @Mapping(target = "createdAt", ignore = true)
        @Mapping(target = "updatedAt", ignore = true)
        CandidateWorkAuthorization toEntity(CreateCandidateWorkAuthorizationRequest request);

        @Mapping(target = "candidateId", source = "candidate.id")
        CandidateWorkAuthorizationResponse toResponse(CandidateWorkAuthorization candidateWorkAuthorization);

        @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
        @Mapping(target = "id", ignore = true)
        @Mapping(target = "candidate", ignore = true)
        @Mapping(target = "country", source = "country", qualifiedByName = "clean")
        @Mapping(target = "createdAt", ignore = true)
        @Mapping(target = "updatedAt", ignore = true)
        void updateEntity(UpdateCandidateWorkAuthorizationRequest request,
                          @MappingTarget CandidateWorkAuthorization candidateWorkAuthorization);
}
