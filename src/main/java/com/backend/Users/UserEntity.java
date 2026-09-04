package com.backend.Users;

import com.backend.Stores.Store.StoreEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long userId;
    private String name;
    private String email;
    private String Phone;
    private String password;

    private boolean verified;
    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId", nullable = false)
    private StoreEntity store;
    @Enumerated(EnumType.STRING)
    private RoleType role;


    @CreationTimestamp
    private LocalDateTime CreatedAt;
    @UpdateTimestamp
    private LocalDateTime UpdatedAt;

}
