package com.backend.Products.Categories.Category.DTOs;

import com.backend.Products.Categories.SubCategory.DTOs.ResponseSubCat;

import java.util.List;

public record ResponseCategory(
        Long categoryId,
        String categoryName,
        List<ResponseSubCat> subCatList
) {
}
