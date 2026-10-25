package com.backend.Products.Catalog.Attributes.DTOs;

import java.util.List;

public record ResponseAttribute(
        Long attributeTypeId,
        String attributeName,
        List<ResponseAttributeValue> attributeValue
) {
}
