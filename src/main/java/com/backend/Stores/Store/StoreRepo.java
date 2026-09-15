package com.backend.Stores.Store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepo extends JpaRepository<StoreEntity, Long> {

    Optional<StoreEntity> findByStoreIdAndActiveTrue(Long tenantId);

    Optional<StoreEntity> findByStoreDomainAndActiveTrue(String storeHost);



}
