package com.backend.Orders.Enums;

public enum PaymentStatus {

    PENDING("Pending", false),
    PAID("Paid", true),
    FAILED("Failed", false),
    REFUNDED("Refunded", false),
    PARTIALLY_REFUNDED("Partially refunded", false);

    private final String displayName;
    private final boolean successful; // money received at some point

    PaymentStatus(String displayName, boolean successful) {
        this.displayName = displayName;
        this.successful = successful;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isSuccessful() {
        return successful;
    }
}