package com.backend.Products.Catalog.Featured;

import com.backend.Global_DTOs.ApiResponseMsg;
import com.backend.Products.Catalog.Featured.DTOs.RequestFeature;
import com.backend.Products.Catalog.Featured.DTOs.ResponseFeature;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/featuredicons")
public class FeaturedController {

    private final FeaturedService serv;

    public FeaturedController(FeaturedService serv) {
        this.serv = serv;
    }

    @PostMapping("/add-feature")
    public ResponseEntity<ApiResponseMsg> addFeature(@RequestBody RequestFeature request){
        ApiResponseMsg response = serv.addFeature(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/get-feature")
    public ResponseEntity<List<ResponseFeature>> getFeature() {
        List<ResponseFeature> response = serv.getFeature();
        return ResponseEntity.ok(response);
    }
}
