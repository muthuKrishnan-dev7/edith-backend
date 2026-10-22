package com.backend.Users.User.Service;

import com.backend.Security.JwtService;
import com.backend.Stores.Store.StoreEntity;
import com.backend.Stores.Store.StoreRepo;
import com.backend.TenantResolve.TenantContext;
import com.backend.Users.Role.RoleEntity;
import com.backend.Users.Role.RoleRepo;
import com.backend.Users.User.DTOs.*;
import com.backend.Users.User.DTOs.LoginDTOs.RequestHardLogin;
import com.backend.Users.User.DTOs.LoginDTOs.RequestPhoneLogin;
import com.backend.Users.User.DTOs.RegisterDTOs.RegisterRequest;
import com.backend.Users.User.DTOs.RegisterDTOs.RequestPhoneRegister;
import com.backend.Users.User.UserEntity;
import com.backend.Users.User.UserRepo;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private static final String DEFAULT_ROLE = "CUSTOMER";

    private final UserRepo repo;
    private final StoreRepo storeRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RoleRepo roleRepo;

    public UserService(UserRepo repo, StoreRepo storeRepo, PasswordEncoder passwordEncoder1, JwtService jwtService, RoleRepo roleRepo) {
        this.repo = repo;
        this.storeRepo = storeRepo;
        this.passwordEncoder = passwordEncoder1;
        this.jwtService = jwtService;
        this.roleRepo = roleRepo;
    }

    //    ===== Hard User Register =====
    public UserResult registerUser(RegisterRequest request) {
        Long storeId = TenantContext.getCurrentTenantId();
        StoreEntity store = storeRepo
                .findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found"));
        if (repo.existsByEmailAndStore_Id(
                request.email(),
                store.getId())) {
            throw new RuntimeException(
                    "User already has an account in this store"
            );
        }
        if (repo.existsByPhoneAndStore_Id(
                request.phone(),
                store.getId())) {
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
        RoleEntity role = roleRepo.findByRole(DEFAULT_ROLE).orElseThrow(()->new RuntimeException("Role Not found"));
        newUser.setRole(role);
        newUser.setVerified(false);
        repo.save(newUser);
        String token = jwtService.generateToken(
                newUser.getId(),
                newUser.getRole().getRole()
        );
        return new UserResult(
                token,
                "Registerd successfull"
        );
    }

//    ===== Easy User Register =====
    public UserResult registerPhone(@Valid RequestPhoneRegister request) {
        Long storeId = TenantContext.getCurrentTenantId();
        StoreEntity store = storeRepo
                .findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found"));
        if (repo.existsByPhoneAndStore_Id(
                request.phone(),
                store.getId())) {
            throw new RuntimeException(
                    "User already has an account in this store"
            );
        }
        UserEntity newUser = new UserEntity();
        newUser.setName(request.name());
        newUser.setPhone(request.phone());
        newUser.setStore(store);
        RoleEntity role = roleRepo.findById(request.role()).orElseThrow(()->new RuntimeException("Role Not found"));
        newUser.setRole(role);
        newUser.setVerified(false);
        repo.save(newUser);
        String token = jwtService.generateToken(
                newUser.getId(),
                newUser.getRole().getRole()
        );
        return new UserResult(
                token,
                "Registerd successfull"
        );
}

//    ===== Hard User Login =====
    public UserResult loginUser(RequestHardLogin request) {

        Long storeId = TenantContext.getCurrentTenantId();
        StoreEntity store = storeRepo
                .findById(storeId)
                .orElseThrow(() -> new RuntimeException("Invalid Store credential"));
        Optional<UserEntity> user;
        if (request.username().contains("@")) {
            user = repo.findByEmailAndStore_Id(
                    request.username(),
                    store.getId()
            );
        } else {
            user = repo.findByPhoneAndStore_Id(
                    request.username(),
                    store.getId()
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
                existingUser.getId(),
                existingUser.getRole().getRole()
        );
        return new UserResult(
                token,
                "Login successfull"
        );
    }

//    ===== Easy User Login =====
    public UserResult easyloginUser(@Valid RequestPhoneLogin request) {
        Long storeId = TenantContext.getCurrentTenantId();
        StoreEntity store = storeRepo
                .findById(storeId)
                .orElseThrow(() -> new RuntimeException("Invalid Store credential"));
        Optional<UserEntity> user  = repo.findByPhoneAndStore_Id(
                    request.phone(),
                    store.getId()
            );

        UserEntity existingUser = user.orElseThrow(() -> new RuntimeException("Invalid User"));

        String token = jwtService.generateToken(
                existingUser.getId(),
                existingUser.getRole().getRole()
        );
        return new UserResult(
                token,
                "Login successfull"
        );
    }


}
