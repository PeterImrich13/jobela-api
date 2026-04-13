package com.jobela.jobela_api.candidate.mapper;


import com.jobela.jobela_api.candidate.dto.request.document.CreateCandidateDocumentRequest;
import com.jobela.jobela_api.candidate.dto.request.document.UpdateCandidateDocumentRequest;
import com.jobela.jobela_api.candidate.dto.response.document.CandidateDocumentResponse;
import com.jobela.jobela_api.candidate.entity.CandidateDocument;
import com.jobela.jobela_api.common.mapper.StringMapperHelper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = StringMapperHelper.class)
public interface CandidateDocumentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "candidate", ignore = true)
    @Mapping(target = "fileName", source = "fileName", qualifiedByName = "clean")
    @Mapping(target = "fileUrl", source = "fileUrl", qualifiedByName = "clean")
    @Mapping(target = "mimeType", source = "mimeType", qualifiedByName = "clean")
    @Mapping(target = "uploadedAt", ignore = true)
    CandidateDocument toEntity(CreateCandidateDocumentRequest request);

    @Mapping(target = "candidateId", source = "candidate.id")
    CandidateDocumentResponse toResponse(CandidateDocument candidateDocument);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "candidate", ignore = true)
    @Mapping(target = "fileName", source = "fileName", qualifiedByName = "clean")
    @Mapping(target = "fileUrl", source = "fileUrl", qualifiedByName = "clean")
    @Mapping(target = "mimeType", source = "mimeType", qualifiedByName = "clean")
    @Mapping(target = "uploadedAt", ignore = true)
    void updateEntity(UpdateCandidateDocumentRequest request, @MappingTarget CandidateDocument candidateDocument);
}
