package com.backend.Users;

import com.backend.Global_DTOs.ApiResponseMsg;
import com.backend.Security.JwtService;
import com.backend.Stores.Store.StoreEntity;
import com.backend.Stores.Store.StoreRepo;
import com.backend.Users.DTOs.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepo repo;
    private final StoreRepo storeRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepo repo, StoreRepo storeRepo, PasswordEncoder passwordEncoder1, JwtService jwtService) {
        this.repo = repo;
        this.storeRepo = storeRepo;
        this.passwordEncoder = passwordEncoder1;
        this.jwtService = jwtService;
    }

//    ===================================== USERS AUTHENTICATION =====================================

    //    ===== Register the User =====
    public UserResult registerUser(RegisterRequest request) {

        StoreEntity store = storeRepo
                .findByStoreDomainAndActiveTrue(request.storeHost())
                .orElseThrow(() -> new RuntimeException("Store not found"));

        if (repo.existsByEmailAndStore_StoreId(
                request.email(),
                store.getStoreId())) {
            throw new RuntimeException(
                    "User already has an account in this store"
            );
        }

        UserEntity newUser = new UserEntity();

        newUser.setName(request.name());
        newUser.setEmail(request.email());
        newUser.setPhone(request.phone());
        newUser.setPassword(passwordEncoder.encode(request.password()));

        newUser.setStore(store);

        if (request.purpose().equals("STORE_APPLICANT")) {
            newUser.setRole(RoleType.STORE_APPLICANT);
        } else {
            newUser.setRole(RoleType.CUSTOMER);
        }


        newUser.setVerified(false);
        newUser.setActive(true);

        repo.save(newUser);

        String token = jwtService.generateToken(
                newUser.getUserId(),
                newUser.getRole().name()
        );

        return new UserResult(
                token,
                "Registerd successfull"
        );
    }

    //    ===== Login User =====
    public UserResult loginUser(LoginRequest request) {
        StoreEntity store = storeRepo
                .findByStoreDomainAndActiveTrue(request.storeHost())
                .orElseThrow(() -> new RuntimeException("Invalid Store credential"));

        Optional<UserEntity> user;

        if (request.username().contains("@")) {
            user = repo.findByEmailAndStore_StoreId(
                    request.username(),
                    store.getStoreId()
            );
        } else {
            user = repo.findByPhoneAndStore_StoreId(
                    request.username(),
                    store.getStoreId()
            );
        }

        UserEntity existingUser = user.orElseThrow(() -> new RuntimeException("Invalid User"));

        if (!passwordEncoder.matches(
                request.password(),
                existingUser.getPassword()
        )) {
            throw new RuntimeException("Invalid Password");
        }

        String token = jwtService.generateToken(
                existingUser.getUserId(),
                existingUser.getRole().name()
        );

        return new UserResult(
                token,
                "Login successfull"
        );

    }


//    ===================================== USERS AUTHENTICATION =====================================

    public UserResult storeAdminRegister(RegisterRequest request) {
        if (!request.storeHost().equals("edith.com")) {
            throw new RuntimeException("Invalid store path. Please user edith.com to register your store.");
        }

        UserEntity newUser = new UserEntity();

        newUser.setName(request.name());
        newUser.setEmail(request.email());
        newUser.setPhone(request.phone());
        newUser.setPassword(passwordEncoder.encode(request.password()));

        newUser.setRole(RoleType.STORE_APPLICANT);

//       --- Later I want the OTP verification to verify make true ---
        newUser.setVerified(true);
        newUser.setActive(true);

        repo.save(newUser);

        String token = jwtService.generateToken(
                newUser.getUserId(),
                newUser.getRole().name()
        );

        return new UserResult(
                token,
                "StoreOwner Registerd successfull"
        );

    }


}
