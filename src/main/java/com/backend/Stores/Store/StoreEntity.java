package com.backend.Stores.Store;

import com.backend.Users.UserEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.UpdateTimestamp;

import javax.swing.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "store")
public class StoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long storeId;

    @Column(unique = true, nullable = false)
    private String storeCode;
    private String storeHost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_owner_id", nullable = false)
    private UserEntity storeOwner;

    private boolean active;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
