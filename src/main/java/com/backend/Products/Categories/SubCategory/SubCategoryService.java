package com.backend.Products.Categories.SubCategory;

import com.backend.Products.Categories.SubCategory.DTOs.ResponseSubCat;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubCategoryService {

    private final SubCategoryRepo repo;

    public SubCategoryService(SubCategoryRepo repo) {
        this.repo = repo;
    }

//    ===== Get All SubCategories =====
    public List<ResponseSubCat> getAllSubCategories() {
        return repo.findAllActiveSubCategories();
    }
}
