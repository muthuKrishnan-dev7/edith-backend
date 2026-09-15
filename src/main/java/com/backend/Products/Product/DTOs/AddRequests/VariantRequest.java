package com.backend.Products.Product.DTOs.AddRequests;

import java.math.BigDecimal;
import java.util.List;

public record VariantRequest(
        Long attributeValue,
        Long color,
        Integer stockQuantity,
        BigDecimal price,
        BigDecimal offerPrice,
        List<ImageRequest> images
) {
}
