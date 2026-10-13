package com.backend.Products.Product.DTOs.ProductForm;

import com.backend.Products.Attributes.DTOs.ResponseAttribute;
import com.backend.Products.Categories.Category.DTOs.ResponseCategory;
import com.backend.Products.Color.DTOs.ResponseColor;
import com.backend.Products.Featured.DTOs.ResponseFeature;
import com.backend.Products.Gender.DTOs.ResponseGender;
import com.backend.Products.Material.DTOs.ResponseMaterial;

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
