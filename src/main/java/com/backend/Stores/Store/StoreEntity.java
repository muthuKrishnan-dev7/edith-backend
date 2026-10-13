package com.backend.Stores.Store;

import com.backend.Stores.StoreType.StoreTypeEntity;
import com.backend.Users.User.UserEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "store")
public class StoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String storeCode;
    private String storeDomain;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_type", nullable = false)
    private StoreTypeEntity storeType;   // FASHION, BAKERY, ELECTRONICS, GROCERY  // FK from -> storeTypeEntity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity storeOwner;        // FK from -> UsersTable

    private Boolean active;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}



