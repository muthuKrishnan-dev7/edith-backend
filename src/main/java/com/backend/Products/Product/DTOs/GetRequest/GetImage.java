package com.backend.Products.Product.DTOs.GetRequest;

public record GetImage(
        Long id,
        Long productId,
        Long productVariantId,
        String imgUrl,
        Boolean isPrimary,
        Integer sortOrder
) {
}
