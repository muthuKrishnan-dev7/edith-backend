package com.backend.Products.Core.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity, Long> {

    Optional<ProductEntity> findByStore_IdAndActiveTrue(Long storeId);
    List<ProductEntity> findByStore_Id(Long storeId);
    Optional<ProductEntity> findByIdAndStore_Id(Long productId, Long storeId);

    // ProductRepo
    @Modifying
    @Query(value = "DELETE FROM product WHERE deleted_at <= :now", nativeQuery = true)
    int deleteExpired(@Param("now") LocalDateTime now);

}
