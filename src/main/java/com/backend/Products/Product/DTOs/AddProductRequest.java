package com.backend.Products.Product.DTOs;

import java.math.BigDecimal;

public record AddProductRequest(

        String storeHost,

        String name,
        String description,
        BigDecimal price,
        BigDecimal offerPrice,
        String featured,

        String Gender,
        Long categoryId,
        Long subcategoryId,

        int stockquantity,
        boolean active
) {
}
