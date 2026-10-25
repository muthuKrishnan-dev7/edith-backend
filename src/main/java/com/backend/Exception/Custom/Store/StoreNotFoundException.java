package com.backend.Exception.Custom.Store;

public class StoreNotFoundException extends RuntimeException{

    public StoreNotFoundException(){
        super("Store not found");
    }

}
