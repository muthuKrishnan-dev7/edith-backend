package com.backend.Stores.StorePaymentSetting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StorePaymentRepo extends JpaRepository<StorePaymentEntity, Long> {
    Optional<StorePaymentEntity> findByStore_Id(Long storeId);
}
