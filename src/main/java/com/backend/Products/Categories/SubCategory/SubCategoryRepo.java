package com.backend.Products.Categories.SubCategory;

import com.backend.Products.Categories.SubCategory.DTOs.ResponseSubCat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepo extends JpaRepository<SubCategoryEntity, Long> {

    List<SubCategoryEntity> findByStore_Id(Long storeId);

}
