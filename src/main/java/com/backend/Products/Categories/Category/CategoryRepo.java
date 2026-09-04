package com.backend.Products.Categories.Category;

import com.backend.Products.Categories.Category.DTOs.ResponseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<CategoryEntity, Long> {

    @Query("""
            SELECT new com.backend.Products.Categories.Category.DTOs.ResponseCategory(
            c.categoryId,
            c.categoryName
            )
            FROM CategoryEntity c
            WHERE c.active = true
            """)
    List<ResponseCategory> findAllActiveCategories();

}
