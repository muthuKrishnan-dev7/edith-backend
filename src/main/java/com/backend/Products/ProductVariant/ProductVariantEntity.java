package com.backend.Products.ProductVariant;

import com.backend.Products.Attributes.AttributeValues.AttributeValuesEntity;
import com.backend.Products.Attributes.Color.ColorEntity;
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
    private BigDecimal offerPrice;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}

//private String generateSku(String tenantId, ProductEntity product, AttributeValuesEntity attrValue, ColorEntity color) {
//    String storePrefix = tenantId.substring(0, 4).toUpperCase();   // "STRA" (store A identifier)
//    String productCode = product.getName().substring(0, 3).toUpperCase();  // "TSH"
//    String size = attrValue.getLabel();                            // "M"
//    String colorCode = (color != null) ? color.getName().substring(0, 3).toUpperCase() : "STD";
//    return storePrefix + "-" + productCode + "-" + size + "-" + colorCode + "-" + System.currentTimeMillis() % 1000;
//    // e.g., STRA-TSH-M-BLK-482
//} ===> For --- SKU ---
