package com.backend.Stores.Store.DTOs;

public record StoreProfileRequest(
        String storeName,
        String logoUrl,
        String faviconUrl,

        String primaryColor,
        String secondaryColor,
        String accentColor,

        String metaTitle,
        String metaDescription,
        String ogImageUrl,

        String instagramUrl,
        String whatsappUrl,
        String facebookUrl,
        String youtubeUrl
) {
}
