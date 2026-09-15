package com.backend.Products.Product.DTOs.EditRequest;

import com.backend.Products.Product.DTOs.AddRequests.VariantRequest;

import java.util.List;

public record EditProductRequest(
        Long productId,
        String name,
        String description,
        Long featured,
        Long gender,
        Long categoryId,
        Long subcategoryId,
        Long attributeId,
        Long material,
        Boolean active,
        List<EditVariantRequest> variants
) {
}
