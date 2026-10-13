package com.backend.TenantResolve;

import com.backend.Stores.Store.StoreEntity;
import com.backend.Stores.Store.StoreRepo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;
import java.util.Set;

public class TenantResolverFilter extends OncePerRequestFilter {

    private final StoreRepo storeRepository;

    public TenantResolverFilter(StoreRepo storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // CORS preflight skip
        return "OPTIONS".equalsIgnoreCase(request.getMethod());
    }

    private static final Set<String> PLATFORM_HOSTS = Set.of(
            "edith-adminpanel.vercel.app",
            "localhost:3001"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        try {
            String origin = request.getHeader("Origin");
            if (origin == null || origin.isBlank()) {
                writeError(response, 400, "Origin header is missing");
                return;
            }

            String domain = extractDomain(origin);
            if (domain == null) {
                writeError(response, 400, "Invalid Origin header");
                return;
            }

            // Admin panel: store lookup skip
            if (PLATFORM_HOSTS.contains(domain)) {
                chain.doFilter(request, response);
                return;
            }

            Optional<StoreEntity> store = storeRepository.findByStoreDomainAndActiveTrue(domain);
            if (store.isEmpty()) {
                writeError(response, 404, "Store not found or inactive");
                return;
            }

            TenantContext.setCurrentTenantId(store.get().getId());
            chain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }

    // "http://localhost:5173"      -> "localhost:5173"
    // "https://akshaboutique.com"  -> "akshaboutique.com"
    private String extractDomain(String origin) {
        try {
            URI uri = URI.create(origin);
            String host = uri.getHost();
            if (host == null) return null;
            int port = uri.getPort();
            return (port == -1 ? host : host + ":" + port).toLowerCase();
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private void writeError(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\":\"" + message + "\"}");
    }
}