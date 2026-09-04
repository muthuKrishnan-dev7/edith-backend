package com.backend.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // Get Authorization header
        String authHeader = request.getHeader("Authorization");

        // No JWT → continue request
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Remove "Bearer " and get actual JWT
        String token = authHeader.substring(7);

        try {

            // Extract information from JWT
            Long userId = jwtService.extractUserId(token);
            String role = jwtService.extractRole(token);

            // Create user's authority
            SimpleGrantedAuthority authority =
                    new SimpleGrantedAuthority("ROLE_" + role);

            // Create Authentication object
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userId,
                            null,
                            List.of(authority)
                    );

            // Store authentication in SecurityContext
            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);

        } catch (Exception e) {

            // Invalid / expired JWT
            SecurityContextHolder.clearContext();
        }

        // Continue to next filter
        filterChain.doFilter(request, response);
    }
}