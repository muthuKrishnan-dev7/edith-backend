package com.backend.Orders.OrderItems;

import com.backend.Orders.Orders.OrdersEntity;
import com.backend.Products.Core.Product.ProductEntity;
import com.backend.Products.Core.ProductVariant.ProductVariantEntity;
import com.backend.Stores.Store.StoreEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_items",
        indexes = {
                @Index(columnList = "order_id"),  // I have doubt on it, is it really need ?
                @Index(columnList = "store_id")
        })
@Getter
@Setter
@NoArgsConstructor
public class OrderItemsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private StoreEntity store; // always copy from order.getStore()
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrdersEntity order;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = true)
    private ProductEntity product;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant_id", nullable = true)
    private ProductVariantEntity productVariant;

    // its need size and color ---- from that table not Id add as a String ----

    // ---- Snapshot at order time (later product edits must not change old orders) ----
    @Column(nullable = false)
    private String productName;
    private String productColor;
    private String productSize;
    @Column(nullable = false)
    private Integer quantity;
    private String sku;

    // ---- Amounts ----
    // lineTotal = (unitPrice * quantity) - discountAmount + taxAmount
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal unitPrice;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal discountTotal = BigDecimal.ZERO;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal taxAmount = BigDecimal.ZERO;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal lineTotal;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}