package com.backend.Exception.Custom;

public class StoreNotFoundException extends RuntimeException{

    public StoreNotFoundException(){
        super("Store not found");
    }

}
