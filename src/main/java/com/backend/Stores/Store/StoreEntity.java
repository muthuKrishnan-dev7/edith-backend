package com.backend.Stores.Store;

import com.backend.Stores.StoreType.StoreTypeEntity;
import com.backend.Users.User.UserEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "store")
public class StoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(unique = true)
    private String storeCode;
    @Column(unique = true)
    private String storeDomain;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_type", nullable = true)
    private StoreTypeEntity storeType;

    private Boolean active;

    @Column(nullable = false)
    private LocalDate startAt;
    @Column(nullable = false)
    private LocalDate endAt;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}



