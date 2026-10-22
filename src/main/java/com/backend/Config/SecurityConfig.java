package com.backend.Config;

import com.backend.Security.JwtAuthenticationFilter;
import com.backend.Stores.Store.StoreRepo;
import com.backend.TenantResolve.TenantResolverFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final StoreRepo storeRepo;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(StoreRepo storeRepo, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.storeRepo = storeRepo;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // ---------- Basic setup ----------
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // ---------- Not logged in / dead token -> 401 ----------
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )

                // ---------- Filters (order mukkiyam) ----------
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new TenantResolverFilter(storeRepo), JwtAuthenticationFilter.class)

                // ---------- Route rules ----------
                .authorizeHttpRequests(auth -> auth
                        // /me login venum (permitAll ku MUNNADI irukkanum)
                        .requestMatchers("/api/auth/me").authenticated()

                        .requestMatchers(
                                "/api/auth/admin/login",
                                "/api/auth/admin/register",
                                "/api/auth/admin/logout",
                                "/api/auth/user/easyRegister",
                                "/api/auth/user/login",
                                "/api/auth/user/easyLogin",
                                "/api/auth/user/logout",
                                "/error").permitAll()

                        .requestMatchers(
                                "/api/store/storeCreation")
                        .hasRole("STORE_APPLICANT")

                        .requestMatchers(
//                              ========== PRODUCT API PATHS ==========
                                "/api/product/form-options",
                                "/api/product/addproduct",
                                "/api/product/editproduct",
                                "/api/product/deleteproduct",
                                "/api/product/getAllProduct",
//                              ========== ATTRIBUTES API PATHS ==========
                                "/api/subcategory/addsubcategory",
                                "/api/category/getcategory",
                                "/api/category/getcategory&subcategory",
                                "/api/color/add-color",
                                "/api/color/get-color",
                                "/api/color/delete-color",
                                "/api/material/add-material",
                                "/api/material/get-material",
                                "/api/material/delete-material"
                               )
                        .hasAnyRole("STORE_ADMIN", "STORE_STAFF")
                                .anyRequest().authenticated()
                );

        return http.build();
    }
}