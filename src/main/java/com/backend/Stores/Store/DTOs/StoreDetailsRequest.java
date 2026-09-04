package com.backend.Stores.Store.DTOs;

public record StoreDetailsRequest(

        // Contact
        String email,
        String phone,
        String whatsapp,

        // Address Proof
        String address,
        String city,
        String district,
        String pin_code,
        String state,
        String country,

        // Business / legal
        String gst,
        String pan,
        String paymentAPI

        ) {
}
