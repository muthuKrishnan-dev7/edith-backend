package com.backend.TenantResolve;

public class TenantContext {
    private static final ThreadLocal<Long> currentTenant = new ThreadLocal<>();

    public static void setCurrentTenantId(Long tenantId) {
        currentTenant.set(tenantId);
    }

    public static Long getCurrentTenantId() {
        return currentTenant.get();
    }

    public static void clear() {
        currentTenant.remove();
    }
}
