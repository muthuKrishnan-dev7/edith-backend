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
    @JoinColumn(name = "storeId")
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


//        | Area              | Dynamic data                                        |
//        | ----------------- | --------------------------------------------------- |
//        | **Branding**      | Logo, store name, favicon, brand colors             |
//        | **Header**        | Store name, logo, navigation/categories             |
//        | **Contact**       | Phone, email, WhatsApp, address                     |
//        | **Business**      | Opening hours, delivery availability, minimum order |
//        | **Hero/Banner**   | Banner images, title, subtitle, CTA                 |
//        | **Products**      | Name, images, price, discount, stock, featured      |
//        | **Categories**    | Category name, image, active/inactive               |
//        | **Offers**        | Offer title, discount, coupon, validity             |
//        | **About**         | Store description, owner/business info              |
//        | **Footer**        | Address, contact, social links, policies            |
//        | **Social Media**  | Instagram, Facebook, YouTube etc.                   |
//        | **Policies**      | Return, refund, shipping, privacy, terms            |
//        | **SEO**           | Meta title, description, favicon, OG image          |
//        | **Announcements** | Sale announcement, holiday notice, etc.             |

