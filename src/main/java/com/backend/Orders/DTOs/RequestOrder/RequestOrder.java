package com.backend.Orders.DTOs.RequestOrder;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;
import java.util.List;

public record RequestOrder(
        @NotEmpty
        List<@Valid  RequestOrderItems> items,
        Long addressId,
        RequestAddress address
) {
}