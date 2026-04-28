package com.jobela.jobela_api.security.dto;

import com.jobela.jobela_api.common.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRequest(
        @NotBlank
        @Email
        String email,

        @NotBlank
        String password,

        @NotNull
        UserRole role
) {
}
