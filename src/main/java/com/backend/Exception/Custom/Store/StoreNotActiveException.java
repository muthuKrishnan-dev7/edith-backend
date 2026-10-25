package com.backend.Exception.Custom.Store;

public class StoreNotActiveException extends RuntimeException{

    public StoreNotActiveException(){
        super("Store not active");
    }

}
