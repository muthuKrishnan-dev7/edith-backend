package com.backend.Orders.Orders.DTOs;

import java.math.BigDecimal;

public record ResponseCreateOrder(
        Long orderId,
        String orderNumber,
        String paymentStatus,
        BigDecimal payableAmount
) {
}
