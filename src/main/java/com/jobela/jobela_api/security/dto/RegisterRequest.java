package com.jobela.jobela_api.security.dto;

import com.jobela.jobela_api.common.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRequest(
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password cannot be blank")
        String password,

        @NotNull(message = "User role cannot be null")
        UserRole role
) {
}
