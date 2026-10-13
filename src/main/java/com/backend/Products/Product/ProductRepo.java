package com.backend.Products.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByStore_IdAndActiveTrue(Long storeId);

    Optional<ProductEntity> findByIdAndStore_Id(Long productId, Long storeId);

}
