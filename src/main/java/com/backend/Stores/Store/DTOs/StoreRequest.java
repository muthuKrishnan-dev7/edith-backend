package com.backend.Stores.Store.DTOs;

public record StoreRequest(
        String storeDomain,
        Long storeType
) {
}
