package com.backend.Products.Core.Product.DTOs.GetRequest;

import java.util.List;

public record GetProduct(
        Long productId,
        String name,
        String description,
        String slug,
        Long categoryId,
        String categoryName,
        Long subcategoryId,
        String subcategoryName,
        Long attributeId,
        String attributeName,
        Long gender,
        String genderName,
        Long featured,
        String featureIcon,
        String featureLabel,
        Long material,
        String materialName,
        Boolean active,
        List<GetVariant> variants
) {
}
