package com.backend.Products.Gender;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenderRepo extends JpaRepository<GenderEntity, Long> {
}
