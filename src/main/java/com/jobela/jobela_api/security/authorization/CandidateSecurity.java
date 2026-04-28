package com.jobela.jobela_api.security.authorization;


import com.jobela.jobela_api.candidate.repository.CandidateRepository;
import com.jobela.jobela_api.security.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("candidateSecurity")
@RequiredArgsConstructor
public class CandidateSecurity {

    private final CandidateRepository candidateRepository;

    public boolean isOwner(Long candidateId, Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails userDetails)) {
            return false;
        }

        var currentUserId = userDetails.getUser().getId();

        return candidateRepository.findById(candidateId)
                .map(candidate -> candidate.getUser().getId().equals(currentUserId))
                .orElse(false);
    }

    public boolean isCurrentUser(Long userId, Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails userDetails)) {
            return false;
        }

        return userDetails.getUser().getId().equals(userId);
    }
}