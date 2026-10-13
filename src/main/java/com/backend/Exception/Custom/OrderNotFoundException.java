package com.backend.Exception.Custom;

public class OrderNotFoundException extends RuntimeException{

    public OrderNotFoundException(){
        super("Order not found");
    }

}
