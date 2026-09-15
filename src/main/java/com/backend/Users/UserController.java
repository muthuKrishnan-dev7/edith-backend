package com.backend.Users;

import com.backend.Global_DTOs.ApiResponseMsg;
import com.backend.Users.DTOs.LoginRequest;
import com.backend.Users.DTOs.RegisterRequest;
import com.backend.Users.DTOs.ResponseUserMsg;
import com.backend.Users.DTOs.UserResult;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService serv;


    public UserController(UserService serv) {
        this.serv = serv;
    }

    //    ===== Register the New User =====
    @PostMapping("/register")
    public ResponseEntity<ApiResponseMsg> registerUser(@RequestBody RegisterRequest request, HttpServletResponse response) {

        UserResult result = serv.registerUser(request);

        ResponseCookie cookie = ResponseCookie.from(
                        "access_token",
                        result.token()
                )
                .httpOnly(true)
                .secure(true) // Its allows only share the JWT via Https, so May need false when its run local
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

    //    ===== Login User =====
    @PostMapping("/login")
    public ResponseEntity<ApiResponseMsg> loginUser(@RequestBody LoginRequest request, HttpServletResponse response) {
        UserResult result = serv.loginUser(request);

        ResponseCookie cookie = ResponseCookie.from(
                        "access_token",
                        result.token()
                )
                .httpOnly(true)
                .secure(true)
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

    //    ===== Logout User =====
    @PostMapping("/logout")
    public ResponseEntity<ApiResponseMsg> logoutUser(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from(
                        "access_token",
                        ""
                )
                .httpOnly(true)
                .secure(true)
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

//    ===== Edit User =====
//    ===== Delete User =====


    @PostMapping("/register/tenantowner")
    public ResponseEntity<ApiResponseMsg> storeAdminRegister(@RequestBody RegisterRequest request, HttpServletResponse response) {
        UserResult result = serv.storeAdminRegister(request);

        ResponseCookie cookie = ResponseCookie.from(
                        "access_token",
                        result.token()
                )
                .httpOnly(true)
                .secure(true) // Its allows only share the JWT via Https, so May need false when its run local
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

}
