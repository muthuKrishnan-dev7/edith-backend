package com.backend.Products.Categories.Category.DTOs;

public record RequestCategory(
        Long categoryId,
        String categoryName,
        Boolean active
){
}
