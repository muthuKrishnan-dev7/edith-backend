package com.backend.Products.Catalog.Attributes.AttributeValues;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeValuesRepo extends JpaRepository<AttributeValuesEntity, Long> {

}
