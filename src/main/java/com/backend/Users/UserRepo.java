package com.backend.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {

    boolean existsByEmailAndStore_StoreId(
            String email,
            Long storeId
    );

    Optional<UserEntity> findByEmailAndStore_StoreId(
            String email,
            Long storeId
    );

    Optional<UserEntity> findByPhoneAndStore_StoreId(
            String phone,
            Long storeId
    );
}
