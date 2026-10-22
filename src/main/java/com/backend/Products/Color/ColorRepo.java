package com.backend.Products.Color;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ColorRepo extends JpaRepository<ColorEntity, Long> {

    List<ColorEntity> findByStore_Id(Long storeId);
}
