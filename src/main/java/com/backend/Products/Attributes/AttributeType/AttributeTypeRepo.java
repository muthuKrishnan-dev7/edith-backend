package com.backend.Products.Attributes.AttributeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeTypeRepo extends JpaRepository<AttributeTypeEntity, Long> {
}
