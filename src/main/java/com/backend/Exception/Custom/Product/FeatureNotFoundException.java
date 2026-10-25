package com.backend.Exception.Custom.Product;

public class FeatureNotFoundException extends RuntimeException{

    public FeatureNotFoundException(){
        super("Feature not found.");
    }

}
