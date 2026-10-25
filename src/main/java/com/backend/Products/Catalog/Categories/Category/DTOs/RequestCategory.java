package com.backend.Products.Catalog.Categories.Category.DTOs;

public record RequestCategory(
        Long categoryId,
        String categoryName,
        Boolean active
){
}
