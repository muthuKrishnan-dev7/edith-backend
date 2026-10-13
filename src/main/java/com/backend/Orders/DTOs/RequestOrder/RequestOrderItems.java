package com.backend.Orders.DTOs.RequestOrder;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RequestOrderItems(
        @NotNull
        Long productId,
        @NotNull
        Long variantId,
        @Positive
        Integer quantity
) {
}
