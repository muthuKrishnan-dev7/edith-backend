package com.backend.Products.Product;

import com.backend.Products.Attributes.AttributeType.AttributeTypeEntity;
import com.backend.Products.Categories.Category.CategoryEntity;
import com.backend.Products.Categories.SubCategory.SubCategoryEntity;
import com.backend.Products.Featured.FeaturedEntity;
import com.backend.Products.Gender.GenderEntity;
import com.backend.Products.Material.MaterialEntity;
import com.backend.Stores.Store.StoreEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feature", nullable = true)
    private FeaturedEntity featured;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gender", nullable = false)
    private GenderEntity gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category", nullable = false)
    private CategoryEntity category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subcategory", nullable = false)
    private SubCategoryEntity subcategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attribute_type_id", nullable = false)
    private AttributeTypeEntity attributeType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id")   // nullable — material irrelevant products-ku (e.g. electronics)
    private MaterialEntity material;   // "Cotton", "Polyester" — fixed, same for all variants

    private Boolean active;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
