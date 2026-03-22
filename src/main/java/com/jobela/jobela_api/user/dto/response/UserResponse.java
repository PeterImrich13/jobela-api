package com.jobela.jobela_api.user.dto.response;

import java.time.LocalDateTime;

import com.jobela.jobela_api.common.enums.UserRole;

public record UserResponse(
        Long id,
        String email,
        UserRole role,
        boolean active,
        LocalDateTime createdAt
) {
}
