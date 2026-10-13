package com.backend.Cloudinary.DTOs;

public enum UploadType {
    PRODUCT("products"),
    STORE_DP("dp");

    private final String folder;
    UploadType(String folder) { this.folder = folder; }
    public String getFolder() { return folder; }
}
