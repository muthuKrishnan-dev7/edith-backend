package com.backend.Products.Core.Product.DTOs.AddRequests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ImageRequest(
        @NotBlank(message = "Image public id is required.")
        String publicId,
        @NotNull(message = "Primary flag is required.")
        Boolean isPrimary,
        @NotNull(message = "Image sort order is required.")
        @Min(value = 1, message = "Sort order must be 1 or more.")
        Integer sortOrder
) {
}
