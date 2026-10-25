package com.backend.Products.Core.ProductImages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductImageRepo extends JpaRepository<ProductImageEntity, Long> {

    Optional<ProductImageEntity> findByIdAndProductVariant_Id(Long imgId, Long productVariantId);
    List<ProductImageEntity> findByProductVariant_Id(Long variantId);


    // ProductImageRepo
    @Modifying
    @Query(value = """
    DELETE FROM product_image
    WHERE deleted_at <= :now
       OR product_variant_id IN (SELECT id FROM product_variant WHERE deleted_at <= :now)
    """, nativeQuery = true)
    int deleteExpired(@Param("now") LocalDateTime now);

}