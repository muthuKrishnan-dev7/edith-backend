package com.backend.Products.ProductVariant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductVariantRepo extends JpaRepository<ProductVariantEntity, Long> {

    List<ProductVariantEntity> findByProduct_Id(Long productId);
    Optional<ProductVariantEntity> findByProduct_IdAndId(Long productId, Long variantId);


}
