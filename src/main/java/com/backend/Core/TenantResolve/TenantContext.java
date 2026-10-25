package com.backend.Core.TenantResolve;

public final class TenantContext {

    private static final ThreadLocal<Long> CURRENT_TENANT = new ThreadLocal<>();

    private TenantContext() {}

    public static void setCurrentTenantId(Long tenantId) {
        CURRENT_TENANT.set(tenantId);
    }

    public static Long getCurrentTenantId() {
        return CURRENT_TENANT.get();
    }

    public static Long requireTenantId() {
        Long id = CURRENT_TENANT.get();
        if (id == null) {
            throw new IllegalStateException("Tenant not resolved for this request");
        }
        return id;
    }

    public static void clear() {
        CURRENT_TENANT.remove();
    }
}