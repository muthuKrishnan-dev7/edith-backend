package com.backend.Stores.StoreProfile;

import com.backend.Stores.Store.StoreEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "store_profile")
public class StoreProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private StoreEntity store;

    // Store Identity
    private String storeName;
    private String logoUrl;
    private String faviconUrl;

    // Branding
    private String primaryColor;
    private String secondaryColor;
    private String accentColor;

    // SEO
    private String metaTitle;
    private String metaDescription;
    private String ogImageUrl;

    // Social
    private String instagramUrl;
    private String whatsappUrl;
    private String facebookUrl;
    private String youtubeUrl;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}