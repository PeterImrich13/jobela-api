package com.jobela.jobela_api.candidate.mapper;

import com.jobela.jobela_api.candidate.dto.response.profile.CandidatePublicProfileSummaryResponse;
import com.jobela.jobela_api.candidate.entity.Candidate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CandidatePublicProfileMapper {

    @Mapping(target = "candidateId", source = "id")
    @Mapping(target = "targetPosition", source = "candidatePreference.targetPosition")
    @Mapping(target = "openToWork", source = "candidatePreference.openToWork")
    CandidatePublicProfileSummaryResponse toSummaryResponse(Candidate candidate);
}
