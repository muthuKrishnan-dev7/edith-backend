package com.backend.Products.Core.Product.DTOs.AddRequests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record VariantRequest(
        @NotNull(message = "Attribute value is required.")
        Long attributeValue,
        @NotNull(message = "Color is required.")
        Long color,
        @NotNull(message = "Stock quantity is required.")
        @Min(value = 0, message = "Stock cannot be negative.")
        Integer stockQuantity,
        @NotNull(message = "Price is required.")
        @DecimalMin(value = "0.01", message = "Price must be greater than 0.")
        BigDecimal price,
        @DecimalMin(value = "0", message = "Offer price cannot be negative.")
        BigDecimal discount_price,
        @NotEmpty(message = "At least one variant image is required.")
        List<@Valid ImageRequest> images
) {
}
