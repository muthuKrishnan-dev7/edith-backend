package com.backend.Products.Core.ProductVariant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductVariantRepo extends JpaRepository<ProductVariantEntity, Long> {

    List<ProductVariantEntity> findByProduct_Id(Long productId);
    Optional<ProductVariantEntity> findByIdAndProduct_Id(Long variantId, Long productId);

    Integer countByProduct_Id(Long productId);

    // ProductVariantRepo
    @Modifying
    @Query(value = "DELETE FROM product_variant WHERE deleted_at <= :now", nativeQuery = true)
    int deleteExpired(@Param("now") LocalDateTime now);


}
