package com.backend.Users.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotNull(message = "Name is required")
        String name,

        @NotNull(message = "Email is required")
        @Email(message = "Invalid Email")
        String email,

        @NotNull(message = "PhoneNumber is required")
        @Size(min = 10, max = 10, message = "Phone Number must be 10 characters")
        String phone,

        @NotBlank(message = "Password is required")
        @Size(min = 6, max = 6, message = "Password must be exactly 6 characters")
        String password,

        @NotNull(message = "HostName is required")
        String storeHost,

        String purpose

) {
}
