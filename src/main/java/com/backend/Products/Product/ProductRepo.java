package com.backend.Products.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByStore_StoreIdAndActiveTrue(Long storeId);

    List<ProductEntity> findByStore_StoreId(Long storeId);

    Optional<ProductEntity> findByProductIdAndStore_StoreId(Long productId, Long storeId);



}
