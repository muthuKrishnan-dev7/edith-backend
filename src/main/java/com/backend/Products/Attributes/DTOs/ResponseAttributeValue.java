package com.backend.Products.Attributes.DTOs;

public record ResponseAttributeValue(
        Long attributeValueId,
        String label,
        Integer sortOrder
) {
}
