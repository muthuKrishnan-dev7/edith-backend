package com.backend.Orders.Enums;

import java.util.EnumSet;
import java.util.Set;

public enum OrderStatus {

    PENDING("Pending", false),
    CONFIRMED("Confirmed", false),
    PROCESSING("Processing", false),
    SHIPPED("Shipped", false),
    OUT_FOR_DELIVERY("Out for delivery", false),
    DELIVERED("Delivered", true),
    CANCELLED("Cancelled", true),
    RETURNED("Returned", true);

    private final String displayName;
    private final boolean terminal; // no more changes allowed after this

    OrderStatus(String displayName, boolean terminal) {
        this.displayName = displayName;
        this.terminal = terminal;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isTerminal() {
        return terminal;
    }

    // Allowed next statuses - use this in service before changing status
    public Set<OrderStatus> allowedNext() {
        return switch (this) {
            case PENDING -> EnumSet.of(CONFIRMED, CANCELLED);
            case CONFIRMED -> EnumSet.of(PROCESSING, CANCELLED);
            case PROCESSING -> EnumSet.of(SHIPPED, CANCELLED);
            case SHIPPED -> EnumSet.of(OUT_FOR_DELIVERY, DELIVERED);
            case OUT_FOR_DELIVERY -> EnumSet.of(DELIVERED);
            case DELIVERED -> EnumSet.of(RETURNED);
            case CANCELLED, RETURNED -> EnumSet.noneOf(OrderStatus.class);
        };
    }

    public boolean canMoveTo(OrderStatus next) {
        return allowedNext().contains(next);
    }
}