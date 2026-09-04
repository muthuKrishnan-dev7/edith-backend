package com.backend.Products.Categories.Category;

import com.backend.Products.Categories.Category.DTOs.ResponseCategory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepo repo;

    public CategoryService(CategoryRepo repo) {
        this.repo = repo;
    }

    //    ===== Get All Categories =====
    public List<ResponseCategory> getCategories() {
        return repo.findAllActiveCategories();
    }
}
