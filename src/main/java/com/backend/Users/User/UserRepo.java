package com.backend.Users.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {

    Boolean existsByEmailAndStore_Id(
            String email,
            Long storeId
    );

    Boolean existsByPhoneAndStore_Id(
            String phone,
            Long storeId);


//    === User Identify ===
    Optional<UserEntity> findByEmailAndStore_Id(
            String email,
            Long storeId
    );

    Optional<UserEntity> findByPhoneAndStore_Id(
            String phone,
            Long storeId
    );

//    === StoreOwner Identification ===
//    Optional<UserEntity> findByPhone(String phone);
//    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByEmailAndRole_Role(String email, String roleName);
    Optional<UserEntity> findByPhoneAndRole_Role(String phone, String roleName);

}
