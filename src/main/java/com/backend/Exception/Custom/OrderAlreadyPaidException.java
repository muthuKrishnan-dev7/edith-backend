package com.backend.Exception.Custom;

public class OrderAlreadyPaidException extends RuntimeException{

    public OrderAlreadyPaidException() {
        super("Order is already paid");
    }

}
