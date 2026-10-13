package com.backend.Products.Categories.SubCategory;

import com.backend.Global_DTOs.ApiResponseMsg;
import com.backend.Products.Categories.SubCategory.DTOs.RequestSubCat;
import com.backend.Products.Categories.SubCategory.DTOs.ResponseSubCat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subcategory")
public class SubCategoryController {

    private final SubCategoryService serv;

    public SubCategoryController(SubCategoryService serv) {
        this.serv = serv;
    }

    @PostMapping("/addsubcategory")
    public ResponseEntity<ApiResponseMsg> addSubCategory(@RequestBody RequestSubCat request){
        ApiResponseMsg response = serv.addSubCategory(request);
        return ResponseEntity.ok().body(response);
    }

}
