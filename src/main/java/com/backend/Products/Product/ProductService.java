package com.backend.Products.Product;

import com.backend.Global_DTOs.ApiResponseMsg;
import com.backend.Products.Categories.Category.CategoryEntity;
import com.backend.Products.Categories.Category.CategoryRepo;
import com.backend.Products.Categories.Gender.Gender;
import com.backend.Products.Categories.SubCategory.SubCategoryEntity;
import com.backend.Products.Categories.SubCategory.SubCategoryRepo;
import com.backend.Products.Product.DTOs.*;
import com.backend.Stores.Store.StoreEntity;
import com.backend.Stores.Store.StoreRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepo repo;
    private final StoreRepo storerepo;
    private final CategoryRepo categoryrepo;
    private final SubCategoryRepo subCategoryRepo;

    public ProductService(ProductRepo repo, StoreRepo storerepo, CategoryRepo categoryrepo, SubCategoryRepo subCategoryRepo) {
        this.repo = repo;
        this.storerepo = storerepo;
        this.categoryrepo = categoryrepo;
        this.subCategoryRepo = subCategoryRepo;
    }

    //    Add New Product
    public ApiResponseMsg addProduct(AddProductRequest request) {

        StoreEntity store = storerepo.findByStoreHostAndActiveTrue(request.storeHost())
                .orElseThrow(() -> new RuntimeException("Store not found or inactive."));

        ProductEntity addProduct = new ProductEntity();

        addProduct.setStore(store);
        addProduct.setName(request.name());
        addProduct.setDescription(request.description());
        addProduct.setPrice(request.price());
        addProduct.setOfferPrice(request.offerPrice());
        addProduct.setFeatured(request.featured());
        addProduct.setGender(Gender.valueOf(request.Gender()));

        CategoryEntity category = categoryrepo.findById(request.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        addProduct.setCategory(category);

        SubCategoryEntity subCategory = subCategoryRepo.findById(request.subcategoryId())
                .orElseThrow(() -> new RuntimeException("Subcategory not found"));

        addProduct.setSubcategory(subCategory);

        addProduct.setStockQuantity(request.stockquantity());
        addProduct.setActive(request.active());

        repo.save(addProduct);

        return new ApiResponseMsg(
                "Product added successfully."
        );
    }

    //    Edit Product
    public ApiResponseMsg editProduct(EditProductRequest request) {

        ProductEntity editProduct = repo.findById(request.productId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        editProduct.setName(request.name());
        editProduct.setDescription(request.description());
        editProduct.setPrice(request.price());
        editProduct.setOfferPrice(request.offerPrice());
        editProduct.setFeatured(request.featured());

        editProduct.setGender(Gender.valueOf(request.Gender()));
        CategoryEntity category = categoryrepo.findById(request.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        editProduct.setCategory(category);

        SubCategoryEntity subCategory = subCategoryRepo.findById(request.subcategoryId())
                .orElseThrow(() -> new RuntimeException("Subcategory not found"));

        editProduct.setSubcategory(subCategory);

        editProduct.setStockQuantity(request.stockquantity());
        editProduct.setActive(request.active());

        ProductEntity edited = repo.save(editProduct);

        return new ApiResponseMsg(
                "Product Updated successfully."
        );
    }

    //    Get Product By StoreId and True
    private GetProductResponse toResponse(ProductEntity product) {
        return new GetProductResponse(
                product.getProductId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getOfferPrice(),
                product.getFeatured(),
                product.getStore().getStoreId(),
                product.getCategory().getCategoryId(),
                product.getSubcategory().getSubcategoryId(),
                product.getStockQuantity(),
                product.isActive()
        );
    }

    public List<GetProductResponse> getProductsByStoreId(String storeHost) {

        StoreEntity store = storerepo.findByStoreHostAndActiveTrue(storeHost)
                .orElseThrow(() -> new RuntimeException("Store not found or inactive."));

        return repo.findByStore_StoreIdAndActiveTrue(store.getStoreId())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    //    Get All Product for Seller include True and False
    public List<GetProductResponse> getAllProduct(String storeHost) {

        StoreEntity store = storerepo.findByStoreHostAndActiveTrue(storeHost)
                .orElseThrow(() -> new RuntimeException("Store not found or inactive."));

        return repo.findByStore_StoreId(store.getStoreId())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    //    Delete Product
    public ApiResponseMsg deleteProduct(Long productId) {
        ProductEntity product = repo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        repo.delete(product);

        return new ApiResponseMsg(
                "Product removed successfully."
        );
    }

}
