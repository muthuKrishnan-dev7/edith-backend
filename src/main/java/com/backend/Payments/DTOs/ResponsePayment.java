package com.backend.Payments.DTOs;

import java.math.BigDecimal;

public record ResponsePayment(
        Long orderId,
        String razorpay_orderId,
        String razorpay_keyId,
        BigDecimal amount,
        String currency
) {
}
