package com.backend.Users.User;

import com.backend.Global_DTOs.ApiResponseMsg;
import com.backend.Security.JwtService;
import com.backend.Stores.Store.StoreRepo;
import com.backend.Users.User.DTOs.*;
import com.backend.Users.User.DTOs.LoginDTOs.RequestHardLogin;
import com.backend.Users.User.DTOs.LoginDTOs.RequestPhoneLogin;
import com.backend.Users.User.DTOs.RegisterDTOs.RegisterRequest;
import com.backend.Users.User.DTOs.RegisterDTOs.RequestPhoneRegister;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService serv;
    private final JwtService jwtService;

    public UserController(UserService serv, JwtService jwtService, StoreRepo storeRepo) {
        this.serv = serv;
        this.jwtService = jwtService;
    }

//    ===== Hard Register User =====
    @PostMapping("/register")
    public ResponseEntity<ApiResponseMsg> registerUser(@Valid @RequestBody RegisterRequest request, HttpServletResponse response) {
        UserResult result = serv.registerUser(request);
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

//    ===== Easy Register User =====
    @RequestMapping("/easyRegister")
    public ResponseEntity<ApiResponseMsg> registerPhone(@Valid @RequestBody RequestPhoneRegister request, HttpServletResponse response){
        UserResult result = serv.registerPhone(request);
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

//    ===== Hard Login User =====
    @PostMapping("/login/user")
    public ResponseEntity<ApiResponseMsg> loginUser(@Valid @RequestBody RequestHardLogin request, HttpServletResponse response) {
        UserResult result = serv.loginUser(request);
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

//    ===== Easy Login User =====
    @PostMapping("/easyLogin")
    public ResponseEntity<ApiResponseMsg> easyloginUser(@Valid @RequestBody RequestPhoneLogin request, HttpServletResponse response) {
        UserResult result = serv.easyloginUser(request);
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

    //    ===== StoreOwner register =====
    @PostMapping("/register/storeowner")
    public ResponseEntity<ApiResponseMsg> storeAdminRegister(@Valid @RequestBody RegisterRequest request, HttpServletResponse response) {
        UserResult result = serv.storeAdminRegister(request);
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
    @PostMapping("/login/storeowner")
    public ResponseEntity<ApiResponseMsg> loginStoreOwner(@Valid @RequestBody RequestHardLogin request, HttpServletResponse response) {
        UserResult result = serv.loginStoreOwner(request);
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

    // ---------- ME ----------
    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication auth,
                                @CookieValue(name = "token", required = false) String token) {

        String role = auth.getAuthorities().iterator().next()
                .getAuthority().replace("ROLE_", "");

        long expiresAt = token != null ? jwtService.extractExpiration(token).getTime() : 0;

        return ResponseEntity.ok(Map.of(
                "userId", auth.getPrincipal(),
                "role", role,
                "expiresAt", expiresAt
        ));
    }

}
