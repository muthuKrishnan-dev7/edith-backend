package com.backend.Shopping.Address;

import com.backend.Stores.Store.StoreEntity;
import com.backend.Users.User.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "user_address")
public class UserAddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private StoreEntity store;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    private String name;
    @Enumerated(EnumType.STRING)
    private AddressLabel label;
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

}