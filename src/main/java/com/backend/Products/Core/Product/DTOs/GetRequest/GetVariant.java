package com.backend.Products.Core.Product.DTOs.GetRequest;

import java.math.BigDecimal;
import java.util.List;

public record GetVariant(
        Long variantId,
        Long productId,
        Long attributeValue,
        String attributeValueName,
        Long color,
        String colorName,
        String hexcode,
        String sku,
        Integer stockQuantity,
        BigDecimal price,
        BigDecimal discount_price,
        List<GetImage> images
) {
}
