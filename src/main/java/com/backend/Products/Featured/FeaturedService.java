package com.backend.Products.Featured;

import com.backend.Products.Featured.DTOs.ResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeaturedService {

    private final FeaturedRepo repo;

    public FeaturedService(FeaturedRepo repo) {
        this.repo = repo;
    }

    //    Get All Feature
    public List<ResponseDTO> getFeature() {
        return repo.findAll()
                .stream()
                .map(feature -> new ResponseDTO(
                        feature.getId(),
                        feature.getIcon(),
                        feature.getName()
                ))
                .toList();
    }

}
