package com.backend.Products.Color;

import com.backend.Global_DTOs.ApiResponseMsg;
import com.backend.Products.Color.DTOs.RequestColor;
import com.backend.Stores.Store.StoreEntity;
import com.backend.Stores.Store.StoreRepo;
import com.backend.TenantResolve.TenantContext;
import org.springframework.stereotype.Service;

@Service
public class ColorService {

    private final ColorRepo repo;
    private final StoreRepo storeRepo;

    public ColorService(ColorRepo repo, StoreRepo storeRepo) {
        this.repo = repo;
        this.storeRepo = storeRepo;
    }

    public ApiResponseMsg addColor(RequestColor request) {
        Long storeId = TenantContext.requireTenantId();
        StoreEntity store = storeRepo.findById(storeId)
                .orElseThrow(()->new RuntimeException("Store Not found / Invalid Credentials."));

        ColorEntity color = new ColorEntity();
        color.setName(request.colorName());
        color.setHexCode(request.hexCode());
        repo.save(color);
        return new ApiResponseMsg("Color added successfully.");
    }
}
