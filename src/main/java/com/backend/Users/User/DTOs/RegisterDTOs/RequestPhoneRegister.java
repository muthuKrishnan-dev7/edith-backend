package com.backend.Users.User.DTOs.RegisterDTOs;

public record RequestPhoneRegister(
        String name,
        String phone,
        Long role
) {
}
