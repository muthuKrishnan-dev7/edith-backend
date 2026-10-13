package com.backend.Users.User.DTOs.LoginDTOs;

import jakarta.validation.constraints.NotNull;

public record RequestPhoneLogin(
        @NotNull(message = "Phone is required")
        String phone
) {
}
