package com.backend.Cloudinary;

import com.backend.Cloudinary.DTOs.UploadSignatureResponse;
import com.backend.Cloudinary.DTOs.UploadType;
import com.backend.TenantResolve.TenantContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

@Service
public class CloudinarySignatureService {

    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    @Value("${cloudinary.api-key}")
    private String apiKey;

    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    public UploadSignatureResponse sign(UploadType type) {
        Long storeId = TenantContext.requireTenantId();

        String folder = "edith/store_" + storeId + "/" + type.getFolder();
        long timestamp = System.currentTimeMillis() / 1000;   // seconds, milliseconds illa

        // alphabetical order: folder, then timestamp. secret kadaisila (separator illa)
        String toSign = "folder=" + folder + "&timestamp=" + timestamp + apiSecret;
        String signature = sha1Hex(toSign);

        return new UploadSignatureResponse(cloudName, apiKey, timestamp, folder, signature);
    }

    private String sha1Hex(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] hash = md.digest(value.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}