package com.backend.Products.Core.ProductImages;

import com.backend.Products.Core.Product.ProductEntity;
import com.backend.Products.Core.ProductVariant.ProductVariantEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "product_images")
public class ProductImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant_id")
    private ProductVariantEntity productVariant;

    @Column(nullable = false)
    private String publicId;
    private Boolean isPrimary;
    private Integer sortOrder;

    LocalDateTime deletedAt;

}
