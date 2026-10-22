package com.backend.Stores.Store.DTOs;

public record RequestCreateStore(
        String storeDomain,
        Long storeType
) {
}
