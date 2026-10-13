package com.backend.Orders.Orders;

import com.backend.Orders.Enums.OrderStatus;
import com.backend.Orders.Enums.PaymentMethod;
import com.backend.Orders.Enums.PaymentStatus;
import com.backend.Stores.Store.StoreEntity;
import com.backend.Users.Address.AddressLabel;
import com.backend.Users.Address.UserAddressEntity;
import com.backend.Users.User.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders",
        uniqueConstraints = @UniqueConstraint(columnNames = {"store_id", "order_number"}),
        indexes = {
                @Index(columnList = "store_id"),
        })
@Data
@NoArgsConstructor
public class OrdersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private StoreEntity store;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "order_number", nullable = false, updatable = false)
    private String orderNumber; // auto generated

    // ---- Status ----
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus = OrderStatus.PENDING;
    // Current payment state (full attempt history is in the payments table)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod = PaymentMethod.NONE;

    // ---- Amounts ----
    // totalAmount = subTotal - discountAmount + taxAmount + shippingAmount + platformFee
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal subTotal = BigDecimal.ZERO;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal discountTotal = BigDecimal.ZERO;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal taxAmount = BigDecimal.ZERO;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal shippingAmount = BigDecimal.ZERO;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal platformFee = BigDecimal.ZERO; // charged to customer, not part of client's base amount
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal lineTotal = BigDecimal.ZERO;

    @Column(nullable = false, length = 3)
    private String currency = "INR";

    private String couponCode;

    // ---- Shipping ----
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = true)
    private UserAddressEntity address;

    @Enumerated(EnumType.STRING)
    @Column(name = "address_label")
    private AddressLabel addressLabel;
    private String recipientName;
    private String phone;
    private String alternatePhone;
    private String email;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    @Pattern(regexp = "\\d{6}", message = "Invalid pincode")
    private String pincode;
    private String country;


    private String customerNote;
    private String trackingNumber;
    private String courierName;

    // ---- Timestamps ----

    @Column(name = "estimated_delivery_date")
    private LocalDate estimatedDeliveryDate;

    private LocalDateTime deliveredAt;
    private LocalDateTime cancelledAt;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}