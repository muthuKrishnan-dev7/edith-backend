package com.backend.Stores.StoreDetails;

import com.backend.Stores.Store.StoreEntity;
import com.backend.Stores.Templates.TemplateEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "store_details")
public class StoreDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private StoreEntity store;       // Unique FK from -> StoreEntity
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "template_id")
//    private TemplateEntity template;   // FK from -> templateEntity

    // Contact
    private String email;
    private String phone;
    private String whatsapp;

    // Location
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String district;
    private String pin_code;
    private String state;
    private String country;

    // Business / Legal
    private String gst;
    private String pan;
    private String paymentAPI;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
