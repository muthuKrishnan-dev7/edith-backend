package com.backend.Cloudinary;

import com.backend.Cloudinary.DTOs.UploadSignatureResponse;
import com.backend.Cloudinary.DTOs.UploadType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UploadController {

    private final CloudinarySignatureService signatureService;

    @GetMapping("/admin/upload-signature")
    @PreAuthorize("hasAnyRole('STORE_ADMIN','STORE_STAFF')")
    public UploadSignatureResponse signature(@RequestParam UploadType type) {
        return signatureService.sign(type);
    }
}