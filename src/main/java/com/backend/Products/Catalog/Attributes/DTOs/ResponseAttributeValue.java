package com.backend.Products.Catalog.Attributes.DTOs;

public record ResponseAttributeValue(
        Long attributeValueId,
        String label,
        Integer sortOrder
) {
}
