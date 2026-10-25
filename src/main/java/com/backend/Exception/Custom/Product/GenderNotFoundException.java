package com.backend.Exception.Custom.Product;

public class GenderNotFoundException extends RuntimeException{

    public GenderNotFoundException(){
        super("Gender not found.");
    }

}
