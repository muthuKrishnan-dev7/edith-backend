package com.backend.TenantResolve;

import com.backend.Stores.Store.StoreEntity;
import com.backend.Stores.Store.StoreRepo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class TenantResolverFilter extends OncePerRequestFilter {

    @Autowired
    private StoreRepo storeRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        try {
            String host = request.getHeader("Host");

            StoreEntity store = storeRepository.findByStoreDomainAndActiveTrue(host)
                    .orElseThrow(() -> new RuntimeException("Store not found or inactive"));

            TenantContext.setCurrentTenantId(store.getStoreId());

            chain.doFilter(request, response);
        } finally {
            TenantContext.clear();   // IMPORTANT — request mudinjadhum clear pannanum
        }
    }

}
