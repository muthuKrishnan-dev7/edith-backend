package com.backend.Products.Catalog.Categories.SubCategory;

import com.backend.Global_DTOs.ApiResponseMsg;
import com.backend.Products.Catalog.Categories.Category.CategoryEntity;
import com.backend.Products.Catalog.Categories.Category.CategoryRepo;
import com.backend.Products.Catalog.Categories.SubCategory.DTOs.RequestSubCat;
import com.backend.Stores.Store.StoreEntity;
import com.backend.Stores.Store.StoreRepo;
import com.backend.Core.TenantResolve.TenantContext;
import org.springframework.stereotype.Service;

@Service
public class SubCategoryService {

    private final SubCategoryRepo repo;
    private final CategoryRepo categoryRepo;
    private final StoreRepo storeRepo;

    public SubCategoryService(SubCategoryRepo repo, CategoryRepo categoryRepo, StoreRepo storeRepo) {
        this.repo = repo;
        this.categoryRepo = categoryRepo;
        this.storeRepo = storeRepo;
    }

    public ApiResponseMsg addSubCategory(RequestSubCat request){
        Long storeId = TenantContext.requireTenantId();
        StoreEntity store = storeRepo.findById(storeId)
                .orElseThrow(()->new RuntimeException("Store Not found / Invalid Credentials."));
        CategoryEntity category = categoryRepo.findById(request.categoryId())
                .orElseThrow(()->new RuntimeException("Category not found."));
        SubCategoryEntity subCategory = new SubCategoryEntity();
        subCategory.setStore(store);
        subCategory.setCategory(category);
        subCategory.setSubcategoryName(request.subcategoryName());
        repo.save(subCategory);
        return new ApiResponseMsg("Subcategory successfully added.");
    }

    public ApiResponseMsg deleteSubcategory(Long id) {
        SubCategoryEntity subCategory = repo.findById(id).orElseThrow(()->new RuntimeException("Subcategory not found."));
        repo.delete(subCategory);
        return new ApiResponseMsg("Subcategory successfully deleted.");
    }
}
