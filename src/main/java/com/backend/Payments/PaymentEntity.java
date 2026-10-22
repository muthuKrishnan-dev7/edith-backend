package com.backend.Payments;

import com.backend.Orders.Enums.PaymentMethod;
import com.backend.Orders.Enums.PaymentStatus;
import com.backend.Orders.Orders.OrdersEntity;
import com.backend.Stores.Store.StoreEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// One row per Razorpay payment attempt. One order can have many payments (retry after failure, refunds).
@Entity
@Table(name = "payments",
        indexes = {
                @Index(columnList = "order_id"),
                @Index(columnList = "store_id"),
                @Index(columnList = "razorpay_order_id")
        })
@Data
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private StoreEntity store;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrdersEntity order;

    // Created first, when you call Razorpay's create-order API
    @Column(nullable = false)
    private String razorpayOrderId;
    // Only known after the customer pays, so nullable. Unique = webhook/verify can't double-process
    @Column(unique = true)
    private String razorpayPaymentId;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;
    @Column(nullable = false, length = 3)
    private String currency = "INR";
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status = PaymentStatus.PENDING;
    @Enumerated(EnumType.STRING)
    private PaymentMethod method; // known only after the attempt

    private String failureReason;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}