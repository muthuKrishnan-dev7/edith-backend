package com.backend.Products.ProductVariant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductVariantRepo extends JpaRepository<ProductVariantEntity, Long> {

    List<ProductVariantEntity> findByProduct_ProductId(Long productId);

}
