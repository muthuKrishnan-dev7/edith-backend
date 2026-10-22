package com.backend.Products.Product;

import com.backend.Global_DTOs.ApiResponseMsg;
import com.backend.Products.Product.DTOs.AddRequests.AddProductRequest;
import com.backend.Products.Product.DTOs.EditRequest.EditProductRequest;
import com.backend.Products.Product.DTOs.GetRequest.GetProduct;
import com.backend.Products.Product.DTOs.ProductForm.ResponseProductForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService serv;
    private final ProductFormService productFormService;

    public ProductController(ProductService serv, ProductFormService productFormService) {
        this.serv = serv;
        this.productFormService = productFormService;
    }

    //    Add New Product
    @PostMapping("/addproduct")
    public ResponseEntity<ApiResponseMsg> addProduct(@RequestBody AddProductRequest request) {
        ApiResponseMsg response = serv.addProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //    Edit Product
    @PatchMapping("/editproduct")
    public ResponseEntity<ApiResponseMsg> editProduct(@RequestBody EditProductRequest request) {
        ApiResponseMsg response = serv.editProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //    Get All Product True and False
    @GetMapping("/getAllProduct")
    public ResponseEntity<List<GetProduct>> getAllProducts() {
        List<GetProduct> response = serv.getAllProduct();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Delete Product
    @DeleteMapping("/deleteproduct")
    public ResponseEntity<ApiResponseMsg> deleteProduct(@PathVariable Long productId) {
        ApiResponseMsg response = serv.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

//    Get all Product
//    Get Product By CategoryId

    @GetMapping("/form-options")
    public ResponseEntity<ResponseProductForm> getFormOptions(){
        ResponseProductForm response = productFormService.getFormOptions();
        return ResponseEntity.ok().body(response);
    }

}
