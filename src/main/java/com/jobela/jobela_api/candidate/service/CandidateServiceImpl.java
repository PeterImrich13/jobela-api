package com.jobela.jobela_api.candidate.service;

import com.jobela.jobela_api.candidate.dto.request.candidate.UpdateCandidateRequest;
import com.jobela.jobela_api.candidate.entity.Candidate;

import com.jobela.jobela_api.candidate.dto.request.candidate.CreateCandidateRequest;
import com.jobela.jobela_api.candidate.dto.response.candidate.CandidateResponse;
import com.jobela.jobela_api.candidate.mapper.CandidateMapper;
import com.jobela.jobela_api.candidate.repository.CandidateRepository;
import com.jobela.jobela_api.common.exception.CandidateAlreadyExistsException;
import com.jobela.jobela_api.common.exception.CandidateNotFoundException;
import com.jobela.jobela_api.common.exception.UserNotFoundException;
import com.jobela.jobela_api.user.entity.User;
import com.jobela.jobela_api.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;
    private final UserRepository userRepository;
    private final CandidateMapper candidateMapper;

    @Override
    public CandidateResponse createCandidate(Long userId, CreateCandidateRequest request) {

        log.info("Creating candidate, id: {}", userId);

        var user = getUserOrThrow(userId);

        if (candidateRepository.existsByUserId(userId)) {
            throw new CandidateAlreadyExistsException("Candidate already exists for userId: " + user);
        }
        var candidate = candidateMapper.toEntity(request);
        candidate.setUser(user);

        var savedCandidate = candidateRepository.save(candidate);

        log.info("Candidate profile created successfully with id: {}", savedCandidate.getId());
        return candidateMapper.toResponse(savedCandidate);
    }

    @Override
    public CandidateResponse getCandidateById(Long candidateId) {

        log.info("Fetching candidate with id: {}", candidateId);

        var candidate = getCandidateByIdOrThrow(candidateId);

        return candidateMapper.toResponse(candidate);
    }

    @Override
    public CandidateResponse getCandidateByUserId(Long userId) {

        log.info("Fetching candidate by userId: {}", userId);

        var candidate = candidateRepository.findByUserId(userId)
                .orElseThrow(() -> new CandidateNotFoundException("Candidate not found for userId: " + userId));

        return candidateMapper.toResponse(candidate);
    }

    @Override
    public CandidateResponse updateCandidate(Long candidateId, UpdateCandidateRequest request) {

        log.info("Updating candidate with id: {}", candidateId);

        var candidate = getCandidateByIdOrThrow(candidateId);

        candidateMapper.updateCandidateFromRequest(request, candidate);

        var updated = candidateRepository.save(candidate);

        log.info("Candidate updated successfully with id: {}", updated.getId());

        return candidateMapper.toResponse(updated);
    }

    @Override
    public void deleteCandidate(Long candidateId) {

        log.info("Deleting candidate with id: {}", candidateId);

        var candidate = getCandidateByIdOrThrow(candidateId);

        candidateRepository.delete(candidate);

        log.info("Candidate successfully deleted, id: {}", candidateId);
    }

    private User getUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
    }

    private Candidate getCandidateByIdOrThrow(Long candidateId) {
        return candidateRepository.findByUserId(candidateId)
                .orElseThrow(() -> new CandidateNotFoundException("Candidate not found with id: " + candidateId));
    }

}
