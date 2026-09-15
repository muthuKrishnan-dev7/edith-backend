package com.backend.Stores.StoreType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreTypeRepo extends JpaRepository<StoreTypeEntity, Long> {
}
