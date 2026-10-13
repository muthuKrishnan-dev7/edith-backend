package com.backend.Products.Attributes.AttributeValues;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttributeValuesRepo extends JpaRepository<AttributeValuesEntity, Long> {

}
