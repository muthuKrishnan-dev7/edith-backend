package com.backend.Products.Product;

import com.backend.Products.Categories.Category.CategoryEntity;
import com.backend.Products.Categories.Gender.Gender;
import com.backend.Products.Categories.SubCategory.SubCategoryEntity;
import com.backend.Stores.Store.StoreEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId", nullable = false)
    private StoreEntity store;

    @Column(nullable = false)
    private String name;
    private String description;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;
    private BigDecimal offerPrice;
    private String featured;

    private Gender gender;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category", nullable = false)
    private CategoryEntity category;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subcategory", nullable = false)
    private SubCategoryEntity subcategory;

    private int stockQuantity;
    private boolean active;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
