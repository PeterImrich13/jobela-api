package com.jobela.jobela_api.security.authorization;

import com.jobela.jobela_api.security.user.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("userSecurity")
public class UserSecurity {

    public boolean isCurrentUser(Long userId, Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails userDetails)) {
            return false;
        }

        return userDetails.getUser().getId().equals(userId);
    }
}
