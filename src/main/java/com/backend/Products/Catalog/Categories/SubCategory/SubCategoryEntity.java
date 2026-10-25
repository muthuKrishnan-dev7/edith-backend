package com.backend.Products.Catalog.Categories.SubCategory;

import com.backend.Products.Catalog.Categories.Category.CategoryEntity;
import com.backend.Stores.Store.StoreEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "subcategory")
public class SubCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private StoreEntity store;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;
    private String subcategoryName;

}
