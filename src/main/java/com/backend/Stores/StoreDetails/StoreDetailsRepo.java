package com.backend.Stores.StoreDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreDetailsRepo extends JpaRepository<StoreDetailsEntity, Long> {
}
