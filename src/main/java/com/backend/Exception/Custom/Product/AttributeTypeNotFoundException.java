package com.backend.Exception.Custom.Product;

public class AttributeTypeNotFoundException extends RuntimeException {
    public  AttributeTypeNotFoundException(){
        super("Attribute Type not found.");
    }
}
