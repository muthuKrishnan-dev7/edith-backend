package com.backend.Products.Featured;

import com.backend.Global_DTOs.ApiResponseMsg;
import com.backend.Products.Featured.DTOs.RequestFeature;
import com.backend.Products.Featured.DTOs.ResponseFeature;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeaturedService {

    private final FeaturedRepo repo;

    public FeaturedService(FeaturedRepo repo) {
        this.repo = repo;
    }

    public ApiResponseMsg addFeature(RequestFeature request){
        FeaturedEntity feature = new FeaturedEntity();
        feature.setIcon(request.icon());
        feature.setLabel(request.label());
        repo.save(feature);
        return new ApiResponseMsg("Featured added successfully.");
    }

    public List<ResponseFeature> getFeature() {
        return repo.findAll()
                .stream()
                .map(feature -> new ResponseFeature(
                        feature.getId(),
                        feature.getIcon(),
                        feature.getLabel()
                ))
                .toList();
    }

}
