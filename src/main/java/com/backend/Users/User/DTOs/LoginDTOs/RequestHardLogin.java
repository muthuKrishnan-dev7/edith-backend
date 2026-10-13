package com.backend.Users.User.DTOs.LoginDTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RequestHardLogin(
        @NotNull(message = "UserName is required")
        String username,
        @NotBlank(message = "Password is required")
        @Size(min = 6, max = 6, message = "Password must be exactly 6 characters")
        String password
) {
}
