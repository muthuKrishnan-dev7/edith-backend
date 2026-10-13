package com.backend.Products.Categories.Category;

import com.backend.Global_DTOs.ApiResponseMsg;
import com.backend.Products.Categories.Category.DTOs.RequestCategory;
import com.backend.Products.Categories.Category.DTOs.ResponseCategory;
import com.backend.Products.Categories.Category.DTOs.ResponseCategory2;
import com.backend.Products.Categories.SubCategory.DTOs.ResponseSubCat;
import com.backend.Products.Categories.SubCategory.SubCategoryEntity;
import com.backend.Products.Categories.SubCategory.SubCategoryRepo;
import com.backend.TenantResolve.TenantContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepo repo;
    private final SubCategoryRepo subCategoryRepo;

    public CategoryService(CategoryRepo repo, SubCategoryRepo subCategoryRepo) {
        this.repo = repo;
        this.subCategoryRepo = subCategoryRepo;
    }
//    Get Categories and SubCategories
    public List<ResponseCategory> getCategories() {
        Long storeId = TenantContext.getCurrentTenantId();
        if (storeId == null) {
            throw new IllegalStateException("Tenant not resolved for this request");
        }
        List<CategoryEntity> categories = repo.findByActiveTrue();
        List<SubCategoryEntity> allSubs = subCategoryRepo.findByStore_Id(storeId);
        Map<Long, List<SubCategoryEntity>> groupedByCategoryId = allSubs.stream()
                .collect(Collectors.groupingBy(sc->sc.getCategory().getId()));

        return categories.stream()
                .map(cat->new ResponseCategory(
                        cat.getId(),
                        cat.getCategoryName(),
                        groupedByCategoryId
                                .getOrDefault(cat.getId(), List.of())
                                .stream()
                                .map(sc -> new ResponseSubCat(sc.getId(), sc.getSubcategoryName()))
                                .toList()
                ))
                .toList();
    }

//    Get Categories Only
    public List<ResponseCategory2> getCategories2() {
        Long storeId = TenantContext.getCurrentTenantId();
        if (storeId == null) {
            throw new IllegalStateException("Tenant not resolved for this request");
        }
        List<CategoryEntity> categoryEntityList = repo.findByActiveTrue();
        List<ResponseCategory2> response = new ArrayList<>();
        for(CategoryEntity ch : categoryEntityList){
            ResponseCategory2 dto = new ResponseCategory2(
                    ch.getId(),
                    ch.getCategoryName()
            );
            response.add(dto);
        }
        return response;
    }

//    Add Category
    public ApiResponseMsg addCategory(RequestCategory request){
        CategoryEntity category = new CategoryEntity();
        category.setCategoryName(request.categoryName().trim().toUpperCase(Locale.ROOT));
        category.setActive(request.active());
        repo.save(category);
        return new ApiResponseMsg("Category added successfully");
    }

}
