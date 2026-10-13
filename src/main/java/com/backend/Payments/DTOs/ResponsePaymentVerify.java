package com.backend.Payments.DTOs;

public record ResponsePaymentVerify(
        Long orderId,
        String paymentStatus,
        String orderStatus,
        String message
) {
}
