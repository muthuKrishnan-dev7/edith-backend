package com.backend.Products.Catalog.Categories.Category.DTOs;

import com.backend.Products.Catalog.Categories.SubCategory.DTOs.ResponseSubCat;

import java.util.List;

public record ResponseCategory(
        Long categoryId,
        String categoryName,
        List<ResponseSubCat> subCatList
) {
}
