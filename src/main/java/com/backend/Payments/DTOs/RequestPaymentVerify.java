package com.backend.Payments.DTOs;

public record RequestPaymentVerify(
        Long orderId,
        String razorpay_payment_id,
        String razorpay_order_id,
        String razorpay_signature
) {
}
