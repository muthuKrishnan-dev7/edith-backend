package com.backend.Products.Core.Product.Service;

import com.backend.Products.Catalog.Attributes.AttributeType.AttributeTypeEntity;
import com.backend.Products.Catalog.Attributes.AttributeType.AttributeTypeRepo;
import com.backend.Products.Catalog.Attributes.AttributeValues.AttributeValuesEntity;
import com.backend.Products.Catalog.Attributes.AttributeValues.AttributeValuesRepo;
import com.backend.Products.Catalog.Attributes.DTOs.ResponseAttribute;
import com.backend.Products.Catalog.Attributes.DTOs.ResponseAttributeValue;
import com.backend.Products.Catalog.Categories.Category.CategoryEntity;
import com.backend.Products.Catalog.Categories.Category.CategoryRepo;
import com.backend.Products.Catalog.Categories.Category.DTOs.ResponseCategory;
import com.backend.Products.Catalog.Categories.SubCategory.DTOs.ResponseSubCat;
import com.backend.Products.Catalog.Categories.SubCategory.SubCategoryEntity;
import com.backend.Products.Catalog.Categories.SubCategory.SubCategoryRepo;
import com.backend.Products.Catalog.Color.ColorEntity;
import com.backend.Products.Catalog.Color.ColorRepo;
import com.backend.Products.Catalog.Color.DTOs.ResponseColor;
import com.backend.Products.Catalog.Featured.DTOs.ResponseFeature;
import com.backend.Products.Catalog.Featured.FeaturedEntity;
import com.backend.Products.Catalog.Featured.FeaturedRepo;
import com.backend.Products.Catalog.Gender.DTOs.ResponseGender;
import com.backend.Products.Catalog.Gender.GenderEntity;
import com.backend.Products.Catalog.Gender.GenderRepo;
import com.backend.Products.Catalog.Material.DTOs.ResponseMaterial;
import com.backend.Products.Catalog.Material.MaterialEntity;
import com.backend.Products.Catalog.Material.MaterialRepo;
import com.backend.Products.Core.Product.DTOs.ProductForm.ResponseProductForm;
import com.backend.Stores.Store.StoreEntity;
import com.backend.Stores.Store.StoreRepo;
import com.backend.Core.TenantResolve.TenantContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductFormService {

    private final StoreRepo storeRepo;
    private final AttributeTypeRepo attributeTypeRepo;
    private final AttributeValuesRepo attributeValuesRepo;
    private final CategoryRepo categoryRepo;
    private final SubCategoryRepo subCategoryRepo;
    private final ColorRepo colorRepo;
    private final FeaturedRepo featuredRepo;
    private final GenderRepo genderRepo;
    private final MaterialRepo materialRepo;

    public ProductFormService(StoreRepo storeRepo, AttributeTypeRepo attributeTypeRepo, AttributeValuesRepo attributeValuesRepo, CategoryRepo categoryRepo, SubCategoryRepo subCategoryRepo, ColorRepo colorRepo, FeaturedRepo featuredRepo, GenderRepo genderRepo, MaterialRepo materialRepo) {
        this.storeRepo = storeRepo;
        this.attributeTypeRepo = attributeTypeRepo;
        this.attributeValuesRepo = attributeValuesRepo;
        this.categoryRepo = categoryRepo;
        this.subCategoryRepo = subCategoryRepo;
        this.colorRepo = colorRepo;
        this.featuredRepo = featuredRepo;
        this.genderRepo = genderRepo;
        this.materialRepo = materialRepo;
    }

    public ResponseProductForm getFormOptions() {
        Long storeId = TenantContext.requireTenantId();
        StoreEntity store = storeRepo.findById(storeId). orElseThrow(()-> new RuntimeException("Store not found"));

        List<AttributeTypeEntity> attributeType = attributeTypeRepo.findAll();
        List<AttributeValuesEntity> attributeValues = attributeValuesRepo.findAll();
        Map<Long, List<AttributeValuesEntity>> groupByTypeId = attributeValues.stream()
                .collect(Collectors.groupingBy(value -> value.getAttributeType().getId()));

        List<CategoryEntity> categories = categoryRepo.findByActiveTrue();
        List<SubCategoryEntity> subCategory = subCategoryRepo.findByStore_Id(storeId);
        Map<Long, List<SubCategoryEntity>> groupingByCategoryId = subCategory.stream()
                .collect(Collectors.groupingBy(sc->sc.getCategory().getId()));

        List<ColorEntity> colors = colorRepo.findAll();

        List<FeaturedEntity> feature = featuredRepo.findAll();

        List<GenderEntity> gender = genderRepo.findAll();

        List<MaterialEntity> material = materialRepo.findByStore_Id(storeId);

//        List - 1
        List<ResponseAttribute> attributesList = attributeType.stream()
                .map(type -> new ResponseAttribute(
                        type.getId(),
                        type.getAttributeName(),
                        groupByTypeId
                                .getOrDefault(type.getId(), List.of())
                                .stream()
                                .map(val -> new ResponseAttributeValue(
                                        val.getId(),
                                        val.getLabel(),
                                        val.getSortOrder()
                                ))
                                .toList()
                ))
                .toList();

//        List - 2
        List<ResponseCategory> categoryList = categories.stream()
                .map(category -> new ResponseCategory(
                        category.getId(),
                        category.getCategoryName(),
                        groupingByCategoryId
                                .getOrDefault(category.getId(), List.of())
                                .stream()
                                .map(subcat -> new ResponseSubCat(
                                        subcat.getId(),
                                        subcat.getSubcategoryName()
                                ))
                                .toList()
                )).toList();

//        List - 3
        List<ResponseColor> colorList = colors.stream()
                .map(col -> new ResponseColor(
                        col.getId(),
                        col.getName(),
                        col.getHexCode()
                ))
                .toList();

//        List - 4
        List<ResponseFeature> featuresList = feature.stream()
                .map(feat -> new ResponseFeature(
                        feat.getId(),
                        feat.getIcon(),
                        feat.getLabel()
                ))
                .toList();

//        List - 5
        List<ResponseGender> genderList = gender.stream()
                .map(gen -> new ResponseGender(
                        gen.getId(),
                        gen.getGender()
                ))
                .toList();

//        List - 6
        List<ResponseMaterial> materialList = material.stream()
                .map(mat -> new ResponseMaterial(
                        mat.getId(),
                        mat.getMaterialName()
                ))
                .toList();

        return new ResponseProductForm(
                attributesList,
                categoryList,
                colorList,
                featuresList,
                genderList,
                materialList
        );

    }

}
