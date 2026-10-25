package com.backend.Products.Catalog.Categories.SubCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepo extends JpaRepository<SubCategoryEntity, Long> {

    List<SubCategoryEntity> findByStore_Id(Long storeId);

}
