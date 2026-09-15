package com.backend.Products.ProductImages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepo extends JpaRepository<ProductImageEntity, Long> {

    List<ProductImageEntity> findByProductVariant_Id(Long productVariantId);

}
