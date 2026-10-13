package com.backend.Stores.Store.DTOs;

public record StoreDetailsRequest(

        Long templateId,

        // Contact
        String email,
        String phone,
        String whatsapp,

        // Address Proof
        String addressLine1,
        String addressLine2,
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
