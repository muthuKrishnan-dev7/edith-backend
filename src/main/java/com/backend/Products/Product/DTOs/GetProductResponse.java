package com.backend.Products.Product.DTOs;

import java.math.BigDecimal;

public record GetProductResponse(
        Long productId,

        String name,
        String description,
        BigDecimal price,
        BigDecimal offerPrice,
        String featured,

        Long storeId,
        Long categoryId,
        Long subCategoryId,

        int stockQuantity,
        boolean active
) {
}
