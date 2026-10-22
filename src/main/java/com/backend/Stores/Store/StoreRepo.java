package com.backend.Stores.Store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepo extends JpaRepository<StoreEntity, Long> {

    Optional<StoreEntity> findByIdAndActiveTrue(Long tenantId);

    Optional<StoreEntity> findByStoreDomainAndActiveTrue(String storeDomain);

    @Query("select s.id from StoreEntity s where s.user.id = :userId")
    Optional<Long> findIdByUserId(@Param("userId") Long userId);


}
