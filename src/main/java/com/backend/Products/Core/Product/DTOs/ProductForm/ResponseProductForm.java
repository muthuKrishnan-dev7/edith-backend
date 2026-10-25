package com.backend.Products.Core.Product.DTOs.ProductForm;

import com.backend.Products.Catalog.Attributes.DTOs.ResponseAttribute;
import com.backend.Products.Catalog.Categories.Category.DTOs.ResponseCategory;
import com.backend.Products.Catalog.Color.DTOs.ResponseColor;
import com.backend.Products.Catalog.Featured.DTOs.ResponseFeature;
import com.backend.Products.Catalog.Gender.DTOs.ResponseGender;
import com.backend.Products.Catalog.Material.DTOs.ResponseMaterial;

import java.util.List;

public record ResponseProductForm(
        List<ResponseAttribute> attributes,
        List<ResponseCategory> categories,
        List<ResponseColor> color,
        List<ResponseFeature> feature,
        List<ResponseGender> gender,
        List<ResponseMaterial> material
) {
}
