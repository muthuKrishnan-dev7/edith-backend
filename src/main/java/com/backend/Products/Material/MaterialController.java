package com.backend.Products.Material;

import com.backend.Global_DTOs.ApiResponseMsg;
import com.backend.Products.Material.DTOs.RequestMaterial;
import com.backend.Products.Material.DTOs.ResponseMaterial;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/material")
public class MaterialController {

    private final MaterialService serv;

    public MaterialController(MaterialService serv) {
        this.serv = serv;
    }

    @PostMapping("/add-material")
    public ResponseEntity<ApiResponseMsg> addMaterial(@RequestBody RequestMaterial request){
        ApiResponseMsg response = serv.addMaterial(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/get-material")
    public ResponseEntity<List<ResponseMaterial>> getMaterial(){
        List<ResponseMaterial> response = serv.getMaterial();
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/delete-material")
    public  ResponseEntity<ApiResponseMsg> deleteMaterial(@RequestParam Long id){
        ApiResponseMsg responseMsg = serv.deleteMaterial(id);
        return ResponseEntity.ok().body(responseMsg);
    }

}
