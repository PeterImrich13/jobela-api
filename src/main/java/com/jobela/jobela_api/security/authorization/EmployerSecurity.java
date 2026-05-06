package com.jobela.jobela_api.security.authorization;

import com.jobela.jobela_api.employer.repository.EmployerRepository;
import com.jobela.jobela_api.security.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("employerSecurity")
@RequiredArgsConstructor
public class EmployerSecurity {

    private final EmployerRepository employerRepository;

    public boolean isOwner(Long employerId, Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails userDetails)) {
            return false;
        }

        var currentUserId = userDetails.getUser().getId();

        return employerRepository.findById(employerId)
                .map(employer -> employer.getUser().getId().equals(currentUserId))
                .orElse(false);
    }

    public boolean isCurrentUser(Long userId, Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof  CustomUserDetails userDetails)) {
            return false;
        }

        return userDetails.getUser().getId().equals(userId);
    }

}
