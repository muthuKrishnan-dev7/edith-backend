package com.backend.Products.Color;

import com.backend.Global_DTOs.ApiResponseMsg;
import com.backend.Products.Color.DTOs.RequestColor;
import com.backend.Products.Color.DTOs.ResponseColor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/color")
public class ColorController {

    private final ColorService serv;

    public ColorController(ColorService serv) {
        this.serv = serv;
    }

    @PostMapping("/add-color")
    public ResponseEntity<ApiResponseMsg> addColor(@RequestBody RequestColor request){
        ApiResponseMsg response = serv.addColor(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/get-color")
    public ResponseEntity<List<ResponseColor>> getColor(){
        List<ResponseColor> response = serv.getColor();
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/delete-color")
    public ResponseEntity<ApiResponseMsg> deleteColor(@RequestParam Long id){
        ApiResponseMsg responseMsg = serv.deleteColor(id);
        return ResponseEntity.ok().body(responseMsg);
    }

}

