package com.backend.Core.Security;

import com.backend.Stores.Store.StoreRepo;
import com.backend.Core.TenantResolve.TenantContext;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
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
    private final StoreRepo storeRepo;

    public JwtAuthenticationFilter(JwtService jwtService, StoreRepo storeRepo) {
        this.jwtService = jwtService;
        this.storeRepo = storeRepo;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        boolean tenantSetHere = false;

        try {
            String token = extractToken(request);

            if (token != null) {
                try {
                    Long userId = jwtService.extractUserId(token);   // invalid/expired na exception
                    String role = jwtService.extractRole(token);

                    // Store admin / staff: tenant JWT user-oda store-la irundhu varum.
                    // Storefront-la Origin already tenant set pannirundha adhai override pannaadhu.
                    if (isStoreRole(role) && TenantContext.getCurrentTenantId() == null) {
                        Long storeId = storeRepo.findIdByUserId(userId).orElse(null);

                        if (storeId == null) {
                            SecurityContextHolder.clearContext();
                            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                                    "No store linked to this user");
                            return;
                        }
                        TenantContext.setCurrentTenantId(storeId);
                        tenantSetHere = true;
                    }

                    var authentication = new UsernamePasswordAuthenticationToken(
                            userId,
                            null,
                            List.of(new SimpleGrantedAuthority("ROLE_" + role))
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                } catch (JwtException | IllegalArgumentException e) {
                    SecurityContextHolder.clearContext();   // invalid token: auth set aagaadhu
                }
            }

            filterChain.doFilter(request, response);   // oru thadava mattum

        } finally {
            if (tenantSetHere) {
                TenantContext.clear();   // thread reuse-ku munnaadi clean pannanum
            }
        }
    }

    private String extractToken(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if ("access_token".equals(c.getName())) {   // login-la set panna cookie name same-ah irukkanum
                    return c.getValue();
                }
            }
        }
        // Postman testing-ku Bearer fallback (production-la venam na remove pannalaam)
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    private boolean isStoreRole(String role) {
        return "STORE_ADMIN".equals(role) || "STORE_STAFF".equals(role);
    }
}