package com.backend.Products.Material;

import com.backend.Global_DTOs.ApiResponseMsg;
import com.backend.Products.Material.DTOs.RequestMaterial;
import com.backend.Products.Material.DTOs.ResponseMaterial;
import com.backend.Stores.Store.StoreEntity;
import com.backend.Stores.Store.StoreRepo;
import com.backend.TenantResolve.TenantContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialService {

    private final MaterialRepo repo;
    private final StoreRepo storeRepo;

    public MaterialService(MaterialRepo repo, StoreRepo storeRepo) {
        this.repo = repo;
        this.storeRepo = storeRepo;
    }

    public ApiResponseMsg addMaterial(RequestMaterial request) {
        Long storeId = TenantContext.getCurrentTenantId();
        StoreEntity store = storeRepo.findById(storeId).orElseThrow(()-> new RuntimeException("Store Invalid"));
        MaterialEntity material = new MaterialEntity();
        material.setStore(store);
        material.setMaterialName(request.materialName());
        repo.save(material);
        return new ApiResponseMsg("Material Added successfully");
    }

    public List<ResponseMaterial> getMaterial() {
        Long storeId = TenantContext.requireTenantId();
        StoreEntity store = storeRepo.findById(storeId).orElseThrow(()-> new RuntimeException("Store Invalid"));
        List<MaterialEntity> materials = repo.findByStore_Id(storeId);
        return materials.stream()
                .map(m->new ResponseMaterial(m.getId(), m.getMaterialName()))
                .toList();
    }

    public ApiResponseMsg deleteMaterial(Long id) {
        MaterialEntity material = repo.findById(id).orElseThrow(()->new RuntimeException("Material not found:"));
        repo.delete(material);
        return new ApiResponseMsg("Material successfully deleted.");
    }
}
