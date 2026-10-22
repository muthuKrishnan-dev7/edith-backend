package com.backend.Users.User.Controller;

import com.backend.Global_DTOs.ApiResponseMsg;
import com.backend.Users.User.DTOs.LoginDTOs.RequestHardLogin;
import com.backend.Users.User.DTOs.RegisterDTOs.RegisterRequest;
import com.backend.Users.User.DTOs.UserResult;
import com.backend.Users.User.Service.AdminService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/api/auth/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    //    ===== StoreOwner register =====
    @PostMapping("/register")
    public ResponseEntity<ApiResponseMsg> storeAdminRegister(@Valid @RequestBody RegisterRequest request, HttpServletResponse response) {
        UserResult result = adminService.storeAdminRegister(request);
        ResponseCookie cookie = ResponseCookie.from(
                        "access_token",
                        result.token()
                )
                .httpOnly(true)
                .secure(false) // Its allows only share the JWT via Https, so May need false when its run local
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ofDays(30))
                .build();
        response.addHeader(
                HttpHeaders.SET_COOKIE,
                cookie.toString()
        );
        return ResponseEntity.ok(new ApiResponseMsg(result.message()));
    }

    //    ===== StoreOwner login =====
    @PostMapping("/login")
    public ResponseEntity<ApiResponseMsg> loginStoreOwner(@Valid @RequestBody RequestHardLogin request, HttpServletResponse response) {
        UserResult result = adminService.loginStoreOwner(request);
        ResponseCookie cookie = ResponseCookie.from(
                        "access_token",
                        result.token()
                )
                .httpOnly(true)
                .secure(false) // Its allows only share the JWT via Https, so May need false when its run local
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ofHours(1))   // For testing purpose its 1 hour
                .build();
        response.addHeader(
                HttpHeaders.SET_COOKIE,
                cookie.toString()
        );
        return ResponseEntity.ok(new ApiResponseMsg(result.message()));
    }

    //    ===== Logout User =====
    @PostMapping("/logout")
    public ResponseEntity<ApiResponseMsg> logoutUser(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from(
                        "access_token",
                        ""
                )
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(0)
                .build();
        response.addHeader(
                HttpHeaders.SET_COOKIE,
                cookie.toString()
        );
        return ResponseEntity.ok(
                new ApiResponseMsg("Logged out successfully")
        );
    }

}
