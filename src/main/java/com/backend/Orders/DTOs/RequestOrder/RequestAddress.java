package com.backend.Orders.DTOs.RequestOrder;

public record RequestAddress(
        String addressLabel,
        String recipientName,
        String phone,
        String alternatePhone,  // Optional ---
        String email,           // Optional ---
        String addressLine1,
        String addressLine2,
        String city,
        String state,
        String pincode,
        String country
) {
}
