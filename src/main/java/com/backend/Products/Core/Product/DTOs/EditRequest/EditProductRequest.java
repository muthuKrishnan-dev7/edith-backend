package com.backend.Products.Core.Product.DTOs.EditRequest;

import java.util.List;

public record EditProductRequest(
        Long productId,
        String name,
        String description,
        Long featured,
        Boolean active,
        List<EditVariantRequest> variants
) {
}
