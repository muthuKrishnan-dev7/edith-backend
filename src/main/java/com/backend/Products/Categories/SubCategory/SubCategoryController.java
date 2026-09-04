package com.backend.Products.Categories.SubCategory;

import com.backend.Products.Categories.SubCategory.DTOs.ResponseSubCat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/subcategories")
public class SubCategoryController {

    private final SubCategoryService serv;

    public SubCategoryController(SubCategoryService serv) {
        this.serv = serv;
    }

    @GetMapping("/getSubcategories")
    public ResponseEntity<List<ResponseSubCat>> getAllSubCategories(){
        List<ResponseSubCat> response = serv.getAllSubCategories();
        return ResponseEntity.ok(response);
    }

}
