package com.backend.Products.Product;

import com.backend.Global_DTOs.ApiResponseMsg;
import com.backend.Products.Product.DTOs.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService serv;

    public ProductController(ProductService serv) {
        this.serv = serv;
    }

    //    Add New Product
    @PostMapping("/add")
    public ResponseEntity<ApiResponseMsg> addProduct(@RequestBody AddProductRequest request) {
        ApiResponseMsg response = serv.addProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //    Edit Product
    @PatchMapping("/edit")
    public ResponseEntity<ApiResponseMsg> editProduct(@RequestBody EditProductRequest request) {
        ApiResponseMsg response = serv.editProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //    Get Product by storeId and True
    @GetMapping("/getProduct")
    public ResponseEntity<List<GetProductResponse>> getProductsByStoreId(@RequestParam String storeHost) {
        List<GetProductResponse> response = serv.getProductsByStoreId(storeHost);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

//    Get All Product True and False
    @GetMapping("getAllProduct")
    public ResponseEntity<List<GetProductResponse>> getAllProducts(@RequestParam String storeHost){
        List<GetProductResponse> response = serv.getAllProduct(storeHost);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Delete Product
    @DeleteMapping("/deleteProduct")
    public ResponseEntity<ApiResponseMsg> deleteProduct(@PathVariable Long productId) {
        ApiResponseMsg response = serv.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

//    Get all Product
//    Get Product By CategoryId

}
