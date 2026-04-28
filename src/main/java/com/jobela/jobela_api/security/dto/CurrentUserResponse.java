package com.jobela.jobela_api.security.dto;

import com.jobela.jobela_api.common.enums.UserRole;

public record CurrentUserResponse(
        Long id,
        String email,
        UserRole role
) {
}
