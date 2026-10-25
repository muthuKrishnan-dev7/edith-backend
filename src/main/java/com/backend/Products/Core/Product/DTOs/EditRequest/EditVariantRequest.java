package com.backend.Products.Core.Product.DTOs.EditRequest;

import java.math.BigDecimal;
import java.util.List;

public record EditVariantRequest(
        Long VariantId,
        Integer stockQuantity,
        BigDecimal price,
        BigDecimal discountPrice,
        List<EditImageRequest> images
) {
}
