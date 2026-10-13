package com.backend.Products.ProductVariant;

import com.backend.Products.Attributes.AttributeValues.AttributeValuesEntity;
import com.backend.Products.Color.ColorEntity;
import com.backend.Products.Product.ProductEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "product_variant")
public class ProductVariantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attribute_value_id", nullable = false)
    private AttributeValuesEntity attributeValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_id", nullable = false)
    private ColorEntity color;

    @Column(unique = true)
    private String sku;   // Stock_Keeping_Unit => "ST001-TSH-M-BLK-917" subCate-cloth-size-color

    private Integer stockQuantity;
    private BigDecimal price;
    private BigDecimal discount_price;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
