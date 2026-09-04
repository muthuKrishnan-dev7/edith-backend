package com.backend.Products.Categories.SubCategory;

import com.backend.Products.Categories.SubCategory.DTOs.ResponseSubCat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepo extends JpaRepository<SubCategoryEntity, Long> {

    @Query("""
    SELECT new com.backend.Products.Categories.SubCategory.DTOs.ResponseSubCat(
        c.subcategoryId,
        c.subcategoryName
    )
    FROM SubCategoryEntity c
    WHERE c.active = true
""")
    List<ResponseSubCat> findAllActiveSubCategories();

}
