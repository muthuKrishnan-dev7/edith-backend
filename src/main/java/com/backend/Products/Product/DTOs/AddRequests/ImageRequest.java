package com.backend.Products.Product.DTOs.AddRequests;

public record ImageRequest(
        String imgUrl,
        Boolean isPrimary,
        Integer sortOrder
) {
}
