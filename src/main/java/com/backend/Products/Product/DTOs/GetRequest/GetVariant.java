package com.backend.Products.Product.DTOs.GetRequest;

import java.math.BigDecimal;
import java.util.List;

public record GetVariant(
        Long id,
        Long productId,
        Long attributeValue,
        Long color,
        String sku,
        Integer stockQuantity,
        BigDecimal price,
        BigDecimal discount_price,
        List<GetImage> images
) {
}
