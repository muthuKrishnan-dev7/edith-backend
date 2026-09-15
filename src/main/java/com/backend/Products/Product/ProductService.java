package com.backend.Products.Product;

import com.backend.Global_DTOs.ApiResponseMsg;
import com.backend.Products.Attributes.AttributeType.AttributeTypeEntity;
import com.backend.Products.Attributes.AttributeType.AttributeTypeRepo;
import com.backend.Products.Attributes.AttributeValues.AttributeValuesEntity;
import com.backend.Products.Attributes.AttributeValues.AttributeValuesRepo;
import com.backend.Products.Attributes.Color.ColorEntity;
import com.backend.Products.Attributes.Color.ColorRepo;
import com.backend.Products.Categories.Category.CategoryEntity;
import com.backend.Products.Categories.Category.CategoryRepo;
import com.backend.Products.Categories.SubCategory.SubCategoryEntity;
import com.backend.Products.Categories.SubCategory.SubCategoryRepo;
import com.backend.Products.Featured.FeaturedEntity;
import com.backend.Products.Featured.FeaturedRepo;
import com.backend.Products.Gender.GenderEntity;
import com.backend.Products.Gender.GenderRepo;
import com.backend.Products.Material.MaterialEntity;
import com.backend.Products.Material.MaterialRepo;
import com.backend.Products.Product.DTOs.AddRequests.AddProductRequest;
import com.backend.Products.Product.DTOs.AddRequests.ImageRequest;
import com.backend.Products.Product.DTOs.AddRequests.VariantRequest;
import com.backend.Products.Product.DTOs.EditRequest.EditImageRequest;
import com.backend.Products.Product.DTOs.EditRequest.EditProductRequest;
import com.backend.Products.Product.DTOs.EditRequest.EditVariantRequest;
import com.backend.Products.Product.DTOs.GetRequest.GetImage;
import com.backend.Products.Product.DTOs.GetRequest.GetProduct;
import com.backend.Products.Product.DTOs.GetRequest.GetProductResponse;
import com.backend.Products.Product.DTOs.GetRequest.GetVariant;
import com.backend.Products.ProductImages.ProductImageEntity;
import com.backend.Products.ProductImages.ProductImageRepo;
import com.backend.Products.ProductVariant.ProductVariantEntity;
import com.backend.Products.ProductVariant.ProductVariantRepo;
import com.backend.Stores.Store.StoreEntity;
import com.backend.Stores.Store.StoreRepo;
import com.backend.TenantResolve.TenantContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ProductService {

    private final ProductRepo repo;
    private final ProductVariantRepo variantRepo;
    private final ProductImageRepo imageRepo;
    private final StoreRepo storerepo;
    private final CategoryRepo categoryrepo;
    private final SubCategoryRepo subCategoryRepo;
    private final FeaturedRepo featureRepo;
    private final GenderRepo genderRepo;
    private final AttributeTypeRepo attributeTypeRepo;
    private final AttributeValuesRepo attributeValuesRepo;
    private final MaterialRepo materialRepo;
    private final ColorRepo colorRepo;

    public ProductService(ProductRepo repo, ProductVariantRepo variantRepo, ProductImageRepo imageRepo, StoreRepo storerepo, CategoryRepo categoryrepo, SubCategoryRepo subCategoryRepo, FeaturedRepo featureRepo, GenderRepo genderRepo, AttributeTypeRepo attributeTypeRepo, AttributeValuesRepo attributeValuesRepo, MaterialRepo materialRepo, ColorRepo colorRepo) {
        this.repo = repo;
        this.variantRepo = variantRepo;
        this.imageRepo = imageRepo;
        this.storerepo = storerepo;
        this.categoryrepo = categoryrepo;
        this.subCategoryRepo = subCategoryRepo;
        this.featureRepo = featureRepo;
        this.genderRepo = genderRepo;
        this.attributeTypeRepo = attributeTypeRepo;
        this.attributeValuesRepo = attributeValuesRepo;
        this.materialRepo = materialRepo;
        this.colorRepo = colorRepo;
    }

    //    Add New Product
    @Transactional
    public ApiResponseMsg addProduct(AddProductRequest request) {

        Long tenantId = TenantContext.getCurrentTenantId();
        StoreEntity store = storerepo.findById(tenantId)
                .orElseThrow(() -> new RuntimeException("Store not found"));

        ProductEntity addProduct = new ProductEntity();

        addProduct.setStore(store);
        addProduct.setName(request.name());
        addProduct.setDescription(request.description());

        FeaturedEntity feature = featureRepo.findById(request.featured())
                .orElseThrow(() -> new RuntimeException("Featured not found"));
        addProduct.setFeatured(feature);

        GenderEntity gender = genderRepo.findById(request.gender())
                .orElseThrow(() -> new RuntimeException("Gender not found"));
        addProduct.setGender(gender);

        CategoryEntity category = categoryrepo.findById(request.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        addProduct.setCategory(category);

        SubCategoryEntity subcategory = subCategoryRepo.findById(request.subcategoryId())
                .orElseThrow(() -> new RuntimeException("SubCategory not found"));
        addProduct.setSubcategory(subcategory);

        AttributeTypeEntity attributeType = attributeTypeRepo.findById(request.attributeId())
                .orElseThrow(() -> new RuntimeException("AttributeType not found"));
        addProduct.setAttributeType(attributeType);

        MaterialEntity material = materialRepo.findById(request.material())
                .orElseThrow(() -> new RuntimeException("Material not found"));
        addProduct.setMaterial(material);

        addProduct.setActive(request.active());
        repo.save(addProduct);

        List<ProductVariantEntity> variants = new ArrayList<>();
        List<ProductImageEntity> images = new ArrayList<>();
        for (VariantRequest v : request.variants()) {
            ProductVariantEntity newVariant = new ProductVariantEntity();
            newVariant.setProduct(addProduct);

            AttributeValuesEntity value = attributeValuesRepo.findById(v.attributeValue())
                    .orElseThrow(() -> new RuntimeException("Value not found"));
            newVariant.setAttributeValue(value);

            ColorEntity color = colorRepo.findById(v.color())
                    .orElseThrow(() -> new RuntimeException("Color Not found"));
            newVariant.setColor(color);

            newVariant.setSku(generateSku(store, addProduct, v));  // --- Auto Generated Method
            newVariant.setStockQuantity(v.stockQuantity());
            newVariant.setPrice(v.price());
            newVariant.setOfferPrice(v.offerPrice());

            if (v.images() == null || v.images().isEmpty()) {
                throw new RuntimeException("Each variant must have at least 1 image");
            }

            for (ImageRequest img : v.images()) {
                ProductImageEntity newImage = new ProductImageEntity();
                if (!v.images().isEmpty()) {
                    throw new RuntimeException("Each variant must have at least 2 images");
                }
                newImage.setProduct(addProduct);
                newImage.setProductVariant(newVariant);
                newImage.setImgUrl(img.imgUrl());
                newImage.setIsPrimary(img.isPrimary());
                newImage.setSortOrder(img.sortOrder());

                images.add(newImage);
            }

            variants.add(newVariant);
        }

        variantRepo.saveAll(variants);
        imageRepo.saveAll(images);

        return new ApiResponseMsg(
                "Product added successfully."
        );
    }

    private String generateSku(StoreEntity store, ProductEntity product, VariantRequest variant) {

        String storeCode = store.getStoreCode();   // "ST001" — store entity-la already field irukanum

        String subCategoryCode = product.getSubcategory().getSubcategoryName()
                .substring(0, 3)
                .toUpperCase();   // "T-Shirt" -> "TSH"

        String sizeCode = attributeValuesRepo.findById(variant.attributeValue())
                .orElseThrow(() -> new RuntimeException("Attribute value not found"))
                .getLabel();   // "M", "L", "42"

        String colorCode = (variant.color() != null)
                ? colorRepo.findById(variant.color()).get().getName().substring(0, 3).toUpperCase()
                : "STD";   // no color -> "Standard"

        int randomNumber = ThreadLocalRandom.current().nextInt(100, 999);   // 3-digit random

        return String.join("-", storeCode, subCategoryCode, sizeCode, colorCode, String.valueOf(randomNumber));
    }

    //    Edit Product
    @Transactional
    public ApiResponseMsg editProduct(EditProductRequest request) {

        Long tenantId = TenantContext.getCurrentTenantId();
        StoreEntity store = storerepo.findById(tenantId)
                .orElseThrow(() -> new RuntimeException("Store not found"));

        ProductEntity product = repo.findByProductIdAndStore_StoreId(request.productId(), store.getStoreId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(request.name());
        product.setDescription(request.description());

        FeaturedEntity feature = featureRepo.findById(request.featured())
                .orElseThrow(() -> new RuntimeException("Featured not found"));
        product.setFeatured(feature);

        GenderEntity gender = genderRepo.findById(request.gender())
                .orElseThrow(() -> new RuntimeException("Gender not found"));
        product.setGender(gender);

        CategoryEntity category = categoryrepo.findById(request.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        product.setCategory(category);

        SubCategoryEntity subcategory = subCategoryRepo.findById(request.subcategoryId())
                .orElseThrow(() -> new RuntimeException("SubCategory not found"));
        product.setSubcategory(subcategory);

        AttributeTypeEntity attributeType = attributeTypeRepo.findById(request.attributeId())
                .orElseThrow(() -> new RuntimeException("AttributeType not found"));
        product.setAttributeType(attributeType);

        MaterialEntity material = materialRepo.findById(request.material())
                .orElseThrow(() -> new RuntimeException("Material not found"));
        product.setMaterial(material);
        product.setActive(request.active());

        repo.save(product);

        List<ProductVariantEntity> variants = new ArrayList<>();
        List<ProductImageEntity> images = new ArrayList<>();

        for (EditVariantRequest v : request.variants()) {
            ProductVariantEntity varient = variantRepo.findById(v.VariantId())
                    .orElseThrow(() -> new RuntimeException("Variant not found"));

            AttributeValuesEntity value = attributeValuesRepo.findById(v.attributeValue())
                    .orElseThrow(() -> new RuntimeException("Value not found"));
            varient.setAttributeValue(value);

            ColorEntity color = colorRepo.findById(v.color())
                    .orElseThrow(() -> new RuntimeException("Color Not found"));
            varient.setColor(color);

            varient.setStockQuantity(v.stockQuantity());
            varient.setPrice(v.price());
            varient.setOfferPrice(v.offerPrice());

            if (v.images() == null || v.images().isEmpty()) {
                throw new RuntimeException("Each variant must have at least 1 image");
            }

            for (EditImageRequest img : v.images()) {
                ProductImageEntity image = imageRepo.findById(img.imgId())
                        .orElseThrow(() -> new RuntimeException("Image not found"));
                if (!v.images().isEmpty()) {
                    throw new RuntimeException("Each variant must have at least 2 images");
                }
                image.setImgUrl(img.imgUrl());
                image.setSortOrder(img.sortOrder());

                images.add(image);
            }

            variants.add(varient);
        }

        variantRepo.saveAll(variants);
        imageRepo.saveAll(images);


        return new ApiResponseMsg(
                "Product Updated successfully."
        );
    }

    //    Get Product By StoreId
    @Transactional
    public List<GetProduct> getAllProduct() {

        Long tenantId = TenantContext.getCurrentTenantId();

        storerepo.findById(tenantId)
                .orElseThrow(() ->
                        new RuntimeException("Store not found or inactive."));

        List<ProductEntity> products =
                repo.findByStore_StoreIdAndActiveTrue(tenantId);

        List<GetProduct> result = products.stream()
                .map(product -> {

                    List<ProductVariantEntity> variants =
                            variantRepo.findByProduct_ProductId(
                                    product.getProductId()
                            );

                    List<GetVariant> variantResult = variants.stream()
                            .map(variant -> {

                                List<GetImage> images =
                                        imageRepo.findByProductVariant_Id(
                                                        variant.getId()
                                                )
                                                .stream()
                                                .map(image -> new GetImage(
                                                        image.getImageId(),
                                                        image.getProduct().getProductId(),
                                                        image.getProductVariant().getId(),
                                                        image.getImgUrl(),
                                                        image.getIsPrimary(),
                                                        image.getSortOrder()
                                                ))
                                                .toList();

                                return new GetVariant(
                                        variant.getId(),
                                        variant.getProduct().getProductId(),
                                        variant.getAttributeValue().getAttributeValueId(),
                                        variant.getColor().getColorId(),
                                        variant.getSku(),
                                        variant.getStockQuantity(),
                                        variant.getPrice(),
                                        variant.getOfferPrice(),
                                        images
                                );
                            })
                            .toList();

                    return new GetProduct(
                            product.getProductId(),
                            product.getName(),
                            product.getDescription(),
                            product.getFeatured().getId(),
                            product.getGender().getGenderId(),
                            product.getCategory().getCategoryId(),
                            product.getSubcategory().getSubcategoryId(),
                            product.getActive(),
                            variantResult
                    );
                })
                .toList();

        return result;
    }


//    public List<GetProductResponse> getProductsByStoreId(String storeHost) {
//
//        StoreEntity store = storerepo.findByStoreHostAndActiveTrue(storeHost)
//                .orElseThrow(() -> new RuntimeException("Store not found or inactive."));
//
//        return repo.findByStore_StoreIdAndActiveTrue(store.getStoreId())
//                .stream()
//                .map(this::toResponse)
//                .toList();
//    }


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
