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

                        // public (login, logout, error)
                        .requestMatchers("/api/auth/**", "/error").permitAll()

                        // platform admin
                        .requestMatchers("/api/admin/**").hasRole("PLATFORM_ADMIN")

                        // store creation (specific rule ah generic ku MUNNADI)
                        .requestMatchers(
                                "/api/store/storeCreation")
                        .hasRole("STORE_APPLICANT")

                        // Store & Product management
                        .requestMatchers(
                                "/api/store/**",
                                "/api/product/form-options",
                                "/api/product/addproduct",
                                "/api/product/editproduct",
                                "/api/product//deleteProduct"
                               )
                        .hasAnyRole("STORE_ADMIN", "STORE_STAFF")

                        // baaki ellam login venum
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}