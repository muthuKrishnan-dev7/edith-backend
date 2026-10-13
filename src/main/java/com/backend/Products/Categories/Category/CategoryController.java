package com.backend.Products.Categories.Category;

import com.backend.Global_DTOs.ApiResponseMsg;
import com.backend.Products.Categories.Category.DTOs.RequestCategory;
import com.backend.Products.Categories.Category.DTOs.ResponseCategory;
import com.backend.Products.Categories.Category.DTOs.ResponseCategory2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService serv;

    public CategoryController(CategoryService serv) {
        this.serv = serv;
    }

    //    ===== Get All Categories & SubCategories =====
    @GetMapping("/getcategory&subcategory")
    public ResponseEntity<List<ResponseCategory>> getCategories() {
        List<ResponseCategory> response = serv.getCategories();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/getcategory")
    public ResponseEntity<List<ResponseCategory2>> getCategories2(){
        List<ResponseCategory2> response = serv.getCategories2();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/addcategory")
    public ResponseEntity<ApiResponseMsg> addCategory(@RequestBody RequestCategory request){
        ApiResponseMsg responseMsg = serv.addCategory(request);
        return ResponseEntity.ok().body(responseMsg);
    }

}
