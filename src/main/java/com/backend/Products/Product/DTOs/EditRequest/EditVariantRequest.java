package com.backend.Products.Product.DTOs.EditRequest;

import com.backend.Products.Product.DTOs.AddRequests.ImageRequest;

import java.math.BigDecimal;
import java.util.List;

public record EditVariantRequest(
        Long VariantId,
        Long attributeValue,
        Long color,
        Integer stockQuantity,
        BigDecimal price,
        BigDecimal offerPrice,
        List<EditImageRequest> images
) {
}
