package com.backend.Products.Core.Product;

import com.backend.Products.Core.ProductImages.ProductImageRepo;
import com.backend.Products.Core.ProductVariant.ProductVariantRepo;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductPurgeJob {

    private final ProductImageRepo imageRepo;
    private final ProductVariantRepo variantRepo;
    private final ProductRepo productRepo;

    public ProductPurgeJob(ProductImageRepo imageRepo, ProductVariantRepo variantRepo, ProductRepo productRepo) {
        this.imageRepo = imageRepo;
        this.variantRepo = variantRepo;
        this.productRepo = productRepo;
    }

    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    public void purgeExpiredProducts() {
        LocalDateTime now = LocalDateTime.now();
        imageRepo.deleteExpired(now);
        variantRepo.deleteExpired(now);
        productRepo.deleteExpired(now);
    }

}
