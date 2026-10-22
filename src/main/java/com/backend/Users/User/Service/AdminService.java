package com.backend.Users.User.Service;

import com.backend.Security.JwtService;
import com.backend.Users.Role.RoleEntity;
import com.backend.Users.Role.RoleRepo;
import com.backend.Users.User.DTOs.LoginDTOs.RequestHardLogin;
import com.backend.Users.User.DTOs.RegisterDTOs.RegisterRequest;
import com.backend.Users.User.DTOs.UserResult;
import com.backend.Users.User.UserEntity;
import com.backend.Users.User.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AdminService {

    private static final String DEFAULT_ROLE = "STORE_APPLICANT";
    private static final String DEFAULT_ROLE2 = "STORE_OWNER";

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;
    private final JwtService jwtService;

    public AdminService(UserRepo userRepo, PasswordEncoder passwordEncoder, RoleRepo roleRepo, JwtService jwtService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;
        this.jwtService = jwtService;
    }

    public UserResult storeAdminRegister(RegisterRequest request) {

        UserEntity newUser = new UserEntity();
        newUser.setName(request.name());
        newUser.setEmail(request.email());
        newUser.setPhone(request.phone());
        newUser.setPassword(passwordEncoder.encode(request.password()));
        RoleEntity role = roleRepo.findByRole(DEFAULT_ROLE).orElseThrow(()-> new RuntimeException("Role not found"));
        newUser.setRole(role);
        newUser.setVerified(false);
        userRepo.save(newUser);

        String token = jwtService.generateToken(
                newUser.getId(),
                newUser.getRole().getRole()
        );

        return new UserResult(
                token,
                "StoreOwner Registerd successfull"
        );

    }

    public UserResult loginStoreOwner(RequestHardLogin request) {
        String username = request.username();
        Optional<UserEntity> user = username.contains("@")
                ? userRepo.findByEmail(username)
                : userRepo.findByPhone(username);

        UserEntity existingUser = user.orElseThrow(
                () -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.password(), existingUser.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(
                existingUser.getId(),
                existingUser.getRole().getRole()
        );

        return new UserResult(token, "Login successful by storeOwner.");
    }

}
