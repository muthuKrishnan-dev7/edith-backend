package com.backend.Products.Core.Product.DTOs.GetRequest;

public record GetImage(
        Long id,
        Long productVariantId,
        String publicId,
        Boolean isPrimary
) {
}
