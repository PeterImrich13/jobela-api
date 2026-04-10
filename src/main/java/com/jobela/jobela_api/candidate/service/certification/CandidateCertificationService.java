package com.jobela.jobela_api.candidate.service.certification;

import com.jobela.jobela_api.candidate.dto.request.certification.CreateCandidateCertificationRequest;
import com.jobela.jobela_api.candidate.dto.request.certification.UpdateCandidateCertificationRequest;
import com.jobela.jobela_api.candidate.dto.response.certification.CandidateCertificationResponse;

import java.util.List;

public interface CandidateCertificationService {
    CandidateCertificationResponse createCandidateCertification(Long candidateId, CreateCandidateCertificationRequest request);
    CandidateCertificationResponse getCandidateCertificationById(Long candidateId, Long candidateCertificationId);
    List<CandidateCertificationResponse> getAllCandidateCertifications(Long candidateId);
    CandidateCertificationResponse updateCandidateCertification(Long candidateId, Long candidateCertificationId, UpdateCandidateCertificationRequest request);
    void deleteCandidateCertification(Long candidateId, Long candidateCertificationId);
}
