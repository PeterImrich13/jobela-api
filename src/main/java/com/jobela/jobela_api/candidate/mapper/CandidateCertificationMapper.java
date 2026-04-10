package com.jobela.jobela_api.candidate.mapper;


import com.jobela.jobela_api.candidate.dto.request.certification.CreateCandidateCertificationRequest;
import com.jobela.jobela_api.candidate.dto.request.certification.UpdateCandidateCertificationRequest;
import com.jobela.jobela_api.candidate.dto.response.certification.CandidateCertificationResponse;
import com.jobela.jobela_api.candidate.entity.CandidateCertification;
import com.jobela.jobela_api.common.mapper.StringMapperHelper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = StringMapperHelper.class)
public interface CandidateCertificationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "candidate", ignore = true)
    @Mapping(target = "name", source = "name", qualifiedByName = "clean")
    @Mapping(target = "issuer", source = "issuer", qualifiedByName = "clean")
    @Mapping(target = "credentialUrl", source = "credentialUrl", qualifiedByName = "clean")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    CandidateCertification toEntity(CreateCandidateCertificationRequest request);

    @Mapping(target = "candidateId", source = "candidate.id")
    CandidateCertificationResponse toResponse(CandidateCertification candidateCertification);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "candidate", ignore = true)
    @Mapping(target = "name", source = "name", qualifiedByName = "clean")
    @Mapping(target = "issuer", source = "issuer", qualifiedByName = "clean")
    @Mapping(target = "credentialUrl", source = "credentialUrl", qualifiedByName = "clean")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(UpdateCandidateCertificationRequest request, @MappingTarget CandidateCertification candidateCertification);
}
