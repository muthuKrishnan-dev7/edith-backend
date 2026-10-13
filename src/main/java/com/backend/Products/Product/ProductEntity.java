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

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "product",
        uniqueConstraints = @UniqueConstraint(columnNames = {"store_id", "slug"}),
        indexes = {
                @Index(columnList = "store_id")
})
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private StoreEntity store;

    private String name;  // name: "Men's Cotton Round Neck T-Shirt (Red)"
    @Column(columnDefinition = "TEXT")
    private String description;
    private String slug;  // slug: "mens-cotton-round-neck-tshirt-red"
    private BigDecimal ratings;  // 4.5 / 4.7 ...
    private Long ratingCount;  // 2367 ratings ...

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feature_id", nullable = false)
    private FeaturedEntity feature;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gender_id", nullable = false)
    private GenderEntity gender;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subcategory_id", nullable = false)
    private SubCategoryEntity subcategory;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attribute_type_id", nullable = false)
    private AttributeTypeEntity attributeType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", nullable = false)   // nullable — material irrelevant products-ku (e.g. electronics)
    private MaterialEntity material;   // "Cotton", "Polyester" — fixed, same for all variants

    private Boolean active;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
