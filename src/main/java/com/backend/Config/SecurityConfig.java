package com.backend.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {
                })
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**")
                        .permitAll()

                        .requestMatchers("/api/admin/**")
                        .hasRole("PLATFORM_ADMIN")

                        .requestMatchers("/api/store/**")
                        .hasAnyRole("TENANT_OWNER", "TENANT_STAFF")

                        .anyRequest()
                        .authenticated());

        return http.build();
    }

}

