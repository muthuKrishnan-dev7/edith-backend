package com.backend.Products.Categories.Category;

import com.backend.Products.Categories.Category.DTOs.RequestCategory;
import com.backend.Products.Categories.Category.DTOs.ResponseCategory;
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

    //    ===== Get All Categories =====
    @GetMapping("/getcategory")
    public ResponseEntity<List<ResponseCategory>> getCategories() {
        List<ResponseCategory> response = serv.getCategories();
        return ResponseEntity.ok().body(response);
    }

//    ===== Add New Category =====
//    @PostMapping
//    public ResponseEntity<RequestCategory> addCategory(@RequestBody RequestCategory request){
//    }
}
