package com.backend.Products.Color;

import com.backend.Global_DTOs.ApiResponseMsg;
import com.backend.Products.Color.DTOs.RequestColor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}

