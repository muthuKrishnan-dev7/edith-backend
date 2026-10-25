package com.backend.Products.Core.Product.DTOs.GetRequest;

import java.util.List;

public record GetProductResponse(
        List<GetProduct> product
) {
}
