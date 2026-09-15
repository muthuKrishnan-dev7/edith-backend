package com.backend.Products.Product.DTOs.EditRequest;

public record EditImageRequest(
        Long imgId,
        String imgUrl,
        Integer sortOrder
) {
}
