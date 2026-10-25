package com.backend.Products.Catalog.Material;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepo extends JpaRepository<MaterialEntity, Long> {

    List<MaterialEntity> findByStore_Id(Long storeId);

}
