package com.backend.Cloudinary.DTOs;

public record UploadSignatureResponse(
        String cloudName,
        String apiKey,
        Long timestamp,
        String folder,
        String signature
) {
}
