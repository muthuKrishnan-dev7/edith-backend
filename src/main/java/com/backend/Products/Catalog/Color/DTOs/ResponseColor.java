package com.backend.Products.Catalog.Color.DTOs;

public record ResponseColor(
        Long colorId,
        String name,
        String hexCode
) {
}
