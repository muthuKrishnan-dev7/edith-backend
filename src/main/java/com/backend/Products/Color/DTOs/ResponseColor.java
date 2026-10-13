package com.backend.Products.Color.DTOs;

public record ResponseColor(
        Long colorId,
        String name,
        String hexCode
) {
}
