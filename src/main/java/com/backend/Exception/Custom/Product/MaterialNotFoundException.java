package com.backend.Exception.Custom.Product;

public class MaterialNotFoundException extends RuntimeException{

    public MaterialNotFoundException(){
        super("Material not found.");
    }

}
