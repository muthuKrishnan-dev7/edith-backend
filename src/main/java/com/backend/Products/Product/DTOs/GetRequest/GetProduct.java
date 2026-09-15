package com.backend.Products.Product.DTOs.GetRequest;

import java.util.List;

public record GetProduct(
        Long productId,
        String name,
        String description,
        Long featured,
        Long gender,
        Long categoryId,
        Long subCategoryId,
        Boolean active,
        List<GetVariant> variants
) {
}
