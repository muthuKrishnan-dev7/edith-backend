package com.backend.Exception.Custom;

public class StorePaymentNotFound extends RuntimeException{

    public StorePaymentNotFound(){
        super("Store Payment Not Found:");
    }

}
