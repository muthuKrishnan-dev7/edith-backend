package com.backend.Products.Catalog.Color;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorRepo extends JpaRepository<ColorEntity, Long> {

    List<ColorEntity> findByStore_Id(Long storeId);
}
