package com.backend.Products.Product.DTOs.AddRequests;

import java.util.List;

public record AddProductRequest(
        String name,
        String description,
        Long featured,
        Long gender,
        Long categoryId,
        Long subcategoryId,
        Long attributeId,
        Long material,
        Boolean active,
        List<VariantRequest> variants
) {
}
