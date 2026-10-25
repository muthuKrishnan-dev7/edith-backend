package com.backend.Products.Core.Product.DTOs.AddRequests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record AddProductRequest(
        @NotBlank(message = "Product name is required.")
        @Size(max = 150, message = "Product name is too long.")
        String name,
        @NotBlank(message = "Product description is required.")
        @Size(max = 2000, message = "Description is too long.")
        String description,
        @NotNull(message = "Category is required.")
        Long categoryId,
        Long subcategoryId,   // optional (sila category-ku subcategory irukkaadhu)
        @NotNull(message = "Attribute type is required.")
        Long attributeId,
        @NotBlank(message = "Gender is required.")
        Long gender,
        Long featured,        // optional
        Long material,        // optional
        @NotNull(message = "Active status is required.")
        Boolean active,
        @NotEmpty(message = "At least one variant is required.")
        List<@Valid VariantRequest> variants
) {
}
