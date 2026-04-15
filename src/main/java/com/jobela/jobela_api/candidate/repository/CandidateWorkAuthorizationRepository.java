package com.jobela.jobela_api.candidate.repository;

import com.jobela.jobela_api.candidate.entity.CandidateWorkAuthorization;
import com.jobela.jobela_api.common.enums.AuthorizationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CandidateWorkAuthorizationRepository extends JpaRepository<CandidateWorkAuthorization, Long> {
    Optional<CandidateWorkAuthorization> findByIdAndCandidateId(Long candidateWorkAuthorizationId, Long candidateId);
    List<CandidateWorkAuthorization> findAllByCandidateId(Long candidateId);
    Optional<CandidateWorkAuthorization> findByCandidateIdAndCountryIgnoreCaseAndAuthorizationType(
            Long candidateId, String country, AuthorizationType authorizationType);
    boolean existsByCandidateIdAndCountryIgnoreCaseAndAuthorizationType(
            Long candidateId, String country, AuthorizationType authorizationType);
    boolean existsByCandidateIdAndCountryIgnoreCaseAndAuthorizationTypeAndIdNot(
            Long candidateId, String country, AuthorizationType authorizationType, Long candidateWorkAuthorizationId);
}
