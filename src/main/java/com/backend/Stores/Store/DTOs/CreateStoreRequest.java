package com.backend.Stores.Store.DTOs;

public record CreateStoreRequest(
        StoreRequest store,
        StoreDetailsRequest details,
        StoreProfileRequest profile
) {
}
