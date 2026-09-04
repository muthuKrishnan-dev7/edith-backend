package com.backend.Products.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByStore_StoreIdAndActiveTrue(Long storeId);

    List<ProductEntity> findByStore_StoreId(Long storeId);



}
