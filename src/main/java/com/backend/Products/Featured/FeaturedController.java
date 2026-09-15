package com.backend.Products.Featured;

import com.backend.Products.Featured.DTOs.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/featured-Icons")
public class FeaturedController {

    private final FeaturedService serv;

    public FeaturedController(FeaturedService serv) {
        this.serv = serv;
    }

    @GetMapping("/get")
    public ResponseEntity<List<ResponseDTO>> getFeature() {
        List<ResponseDTO> response = serv.getFeature();
        return ResponseEntity.ok(response);
    }
}
