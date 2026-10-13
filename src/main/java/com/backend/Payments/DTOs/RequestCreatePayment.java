package com.backend.Payments.DTOs;

public record RequestCreatePayment(
        Long orderId,
        String paymentMethod  // "RazorPay"
) {
}
