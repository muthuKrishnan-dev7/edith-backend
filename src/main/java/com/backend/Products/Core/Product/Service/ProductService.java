package com.backend.Products.Core.Product.Service;

import com.backend.Exception.Custom.Product.*;
import com.backend.Exception.Custom.Store.StoreNotFoundException;
import com.backend.Global_DTOs.ApiResponseMsg;
import com.backend.Products.Catalog.Attributes.AttributeType.AttributeTypeEntity;
import com.backend.Products.Catalog.Attributes.AttributeType.AttributeTypeRepo;
import com.backend.Products.Catalog.Attributes.AttributeValues.AttributeValuesEntity;
import com.backend.Products.Catalog.Attributes.AttributeValues.AttributeValuesRepo;
import com.backend.Products.Catalog.Color.ColorEntity;
import com.backend.Products.Catalog.Color.ColorRepo;
import com.backend.Products.Catalog.Categories.Category.CategoryEntity;
import com.backend.Products.Catalog.Categories.Category.CategoryRepo;
import com.backend.Products.Catalog.Categories.SubCategory.SubCategoryEntity;
import com.backend.Products.Catalog.Categories.SubCategory.SubCategoryRepo;
import com.backend.Products.Catalog.Featured.FeaturedEntity;
import com.backend.Products.Catalog.Featured.FeaturedRepo;
import com.backend.Products.Catalog.Gender.GenderEntity;
import com.backend.Products.Catalog.Gender.GenderRepo;
import com.backend.Products.Catalog.Material.MaterialEntity;
import com.backend.Products.Catalog.Material.MaterialRepo;
import com.backend.Products.Core.Product.DTOs.AddRequests.AddProductRequest;
import com.backend.Products.Core.Product.DTOs.AddRequests.ImageRequest;
import com.backend.Products.Core.Product.DTOs.AddRequests.VariantRequest;
import com.backend.Products.Core.Product.DTOs.EditRequest.EditImageRequest;
import com.backend.Products.Core.Product.DTOs.EditRequest.EditProductRequest;
import com.backend.Products.Core.Product.DTOs.EditRequest.EditVariantRequest;
import com.backend.Products.Core.Product.DTOs.GetRequest.GetImage;
import com.backend.Products.Core.Product.DTOs.GetRequest.GetProduct;
import com.backend.Products.Core.Product.DTOs.GetRequest.GetVariant;
import com.backend.Products.Core.Product.ProductEntity;
import com.backend.Products.Core.Product.ProductRepo;
import com.backend.Products.Core.ProductImages.ProductImageEntity;
import com.backend.Products.Core.ProductImages.ProductImageRepo;
import com.backend.Products.Core.ProductVariant.ProductVariantEntity;
import com.backend.Products.Core.ProductVariant.ProductVariantRepo;
import com.backend.Stores.Store.StoreEntity;
import com.backend.Stores.Store.StoreRepo;
import com.backend.Core.TenantResolve.TenantContext;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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


//  ============================== Add Product Method ==============================

    @Transactional
    public ApiResponseMsg addProduct(@NotNull AddProductRequest request) {
        Long tenantId = TenantContext.requireTenantId();
        StoreEntity store = storerepo.findById(tenantId)
                .orElseThrow(StoreNotFoundException::new);

        ProductEntity addProduct = new ProductEntity();
        addProduct.setStore(store);
        addProduct.setName(request.name().trim());
        addProduct.setDescription(request.description());
        addProduct.setSlug(generateSlug(request.name()));
//        ============ In future Rating and Rating count ============
        CategoryEntity category = categoryrepo.findById(request.categoryId()).orElseThrow(CategoryNotFoundException::new);
        addProduct.setCategory(category);
        SubCategoryEntity subCategory = null;
        if (request.subcategoryId() != null) {
            subCategory = subCategoryRepo.findById(request.subcategoryId())
                    .orElseThrow(SubCategoryNotFoundException::new);
            if (!subCategory.getCategory().getId().equals(request.categoryId())) {
                throw new IllegalArgumentException("Subcategory does not belong to the selected category.");
            }
        }
        addProduct.setSubcategory(subCategory);
        AttributeTypeEntity attributeType = attributeTypeRepo.findById(request.attributeId())
                        .orElseThrow(AttributeTypeNotFoundException::new);
        addProduct.setAttributeType(attributeType);
        GenderEntity gender = genderRepo.findById(request.gender())
                        .orElseThrow(GenderNotFoundException::new);
        addProduct.setGender(gender);
        FeaturedEntity feature = null;
        if(request.featured() != null){
            feature = featureRepo.findById(request.featured())
                    .orElseThrow(FeatureNotFoundException::new);
        }
        addProduct.setFeature(feature);
        MaterialEntity material = null;
        if(request.material() != null){
            material = materialRepo.findById(request.material())
                    .orElseThrow(MaterialNotFoundException::new);
        }
        addProduct.setMaterial(material);
        addProduct.setActive(request.active());
        repo.save(addProduct);

        // ---------- variants ----------
        List<ProductVariantEntity> variants = new ArrayList<>();
        List<ProductImageEntity> images = new ArrayList<>();
        Set<String> seen = new HashSet<>(); // duplicate (color + attributeValue) check

        for (VariantRequest v : request.variants()) {

            String key = v.color() + "-" + v.attributeValue();
            if (!seen.add(key)) {
                throw new RuntimeException("Duplicate variant (same color and size)");
            }
            if (v.price() == null || v.price().signum() <= 0) {
                throw new RuntimeException("Price must be greater than 0");
            }
            if (v.stockQuantity() == null || v.stockQuantity() < 0) {
                throw new RuntimeException("Stock quantity must be 0 or more");
            }
            if (v.discount_price() != null) {
                if (v.discount_price().signum() <= 0) {
                    throw new IllegalArgumentException("Offer price must be greater than 0");
                }
                if (v.discount_price().compareTo(v.price()) > 0) {
                    throw new IllegalArgumentException("Offer price cannot be greater than price");
                }
            }
            // 1 color = max 2 images, at least 1 (primary)
            if (v.images() == null || v.images().isEmpty()) {
                throw new RuntimeException("Each variant must have at least 1 image");
            }
            if (v.images().size() > 2) {
                throw new RuntimeException("Each variant can have maximum 2 images");
            }

            ProductVariantEntity newVariant = new ProductVariantEntity();
            newVariant.setProduct(addProduct);
            AttributeValuesEntity attributeValue = attributeValuesRepo.findById(v.attributeValue())
                            .orElseThrow(AttributeValueNotFoundException::new);
            if(!attributeValue.getAttributeType().equals(addProduct.getAttributeType())){
                throw new RuntimeException("Attribute Type not match with the value.");
            }
            newVariant.setAttributeValue(attributeValue);
            ColorEntity color = null;
            if(v.color()!=null){
                color = colorRepo.findById(v.color())
                        .orElseThrow(ColorNotFoundException::new);
                if(!color.getStore().getId().equals(store.getId())){
                    throw new RuntimeException("Your Color not match with your Store.");
                }
            }
            newVariant.setColor(color);
            newVariant.setSku(generateSku(store, addProduct, v));
            newVariant.setStockQuantity(v.stockQuantity());
            newVariant.setPrice(v.price());
            newVariant.setDiscount_price(v.discount_price());

            for (ImageRequest img : v.images()) {
                ProductImageEntity newImage = new ProductImageEntity();
                newImage.setProduct(addProduct);
                newImage.setProductVariant(newVariant);
                newImage.setPublicId(img.publicId());
                newImage.setIsPrimary(img.isPrimary());
                newImage.setSortOrder(img.sortOrder());
                images.add(newImage);
            }
            variants.add(newVariant);
        }
        variantRepo.saveAll(variants);
        imageRepo.saveAll(images);

        return new ApiResponseMsg("Product added successfully.");
    }


//  ============================== Edit Product Method ==============================

    @Transactional
    public ApiResponseMsg editProduct(EditProductRequest request) {
        Long tenantId = TenantContext.requireTenantId();

        ProductEntity product = repo.findByIdAndStore_Id(request.productId(), tenantId)
                .orElseThrow(ProductNotFoundException::new);

        product.setName(request.name().trim());
        product.setDescription(request.description());
        product.setActive(request.active());
        product.setFeature(request.featured() == null ? null
                : featureRepo.findById(request.featured())
                .orElseThrow(FeatureNotFoundException::new));

        for (EditVariantRequest v : request.variants()) {
            ProductVariantEntity variant = variantRepo
                    .findByIdAndProduct_Id(v.VariantId(), product.getId())
                    .orElseThrow(ProductVariantNotFoundException::new);

            validatePricing(v.price(), v.discountPrice(), v.stockQuantity());
            variant.setPrice(v.price());
            variant.setDiscount_price(v.discountPrice());
            variant.setStockQuantity(v.stockQuantity());

            if (v.images() == null || v.images().isEmpty() || v.images().size() > 2) {
                throw new IllegalArgumentException("Each variant must have 1 to 2 images");
            }
            for (EditImageRequest img : v.images()) {
                ProductImageEntity image = imageRepo
                        .findByIdAndProductVariant_Id(img.imgId(), variant.getId())
                                .orElseThrow(ImageNotFoundException::new);
                image.setPublicId(img.publicId());
            }
        }
        return new ApiResponseMsg("Product updated successfully.");
    }


//  ============================== GetAll Product Method ==============================

    @Transactional
    public List<GetProduct> getAllProduct() {

        Long tenantId = TenantContext.getCurrentTenantId();
        storerepo.findById(tenantId)
                .orElseThrow(() ->
                        new RuntimeException("Store not found or inactive."));

        List<ProductEntity> products = repo.findByStore_Id(tenantId);
        if (products.isEmpty()) {
            throw new ProductNotFoundException();
        }

        return products.stream()
                .map(product -> {
                    List<ProductVariantEntity> variants = variantRepo.findByProduct_Id(product.getId());
                    List<GetVariant> variantResult = variants.stream()
                            .map(variant -> {
                                List<GetImage> images =
                                        imageRepo.findByProductVariant_Id(
                                                        variant.getId()
                                                )
                                                .stream()
                                                .map(image -> new GetImage(
                                                        image.getId(),
                                                        image.getProductVariant().getId(),
                                                        image.getPublicId(),
                                                        image.getIsPrimary()
                                                ))
                                                .toList();

                                return new GetVariant(
                                        variant.getId(),
                                        variant.getProduct().getId(),
                                        variant.getAttributeValue().getId(),
                                        variant.getAttributeValue().getLabel(),
                                        variant.getColor().getId(),
                                        variant.getColor().getName(),
                                        variant.getColor().getHexCode(),
                                        variant.getSku(),
                                        variant.getStockQuantity(),
                                        variant.getPrice(),
                                        variant.getDiscount_price(),
                                        images
                                        );
                            })
                            .toList();

                    return new GetProduct(
                            product.getId(),
                            product.getName(),
                            product.getDescription(),
                            product.getSlug(),
                            product.getCategory().getId(),
                            product.getCategory().getCategoryName(),
                            product.getSubcategory().getId(),
                            product.getSubcategory().getSubcategoryName(),
                            product.getAttributeType().getId(),
                            product.getAttributeType().getAttributeName(),
                            product.getGender().getId(),
                            product.getGender().getGender(),
                            product.getFeature().getId(),
                            product.getFeature().getIcon(),
                            product.getFeature().getLabel(),
                            product.getMaterial().getId(),
                            product.getMaterial().getMaterialName(),
                            product.getActive(),
                            variantResult
                    );
                })
                .toList();
    }


//  ============================== Delete Product Method ==============================

    @Transactional
    public ApiResponseMsg deleteProduct(Long productId, Long variantId) {
        Long tenantId = TenantContext.requireTenantId();

        ProductEntity product = repo.findByIdAndStore_Id(productId, tenantId)
                .orElseThrow(ProductNotFoundException::new);
        ProductVariantEntity variant = variantRepo.findByIdAndProduct_Id(variantId, productId)
                .orElseThrow(ProductVariantNotFoundException::new);
        List<ProductImageEntity> image = imageRepo.findByProductVariant_Id(variant.getId());

        LocalDateTime purgeAt = LocalDateTime.now().plusDays(10); // config, 10

        long remaining = variantRepo.countByProduct_Id(productId); // ithu active variants mattum
        variant.setDeletedAt(purgeAt);
        for (ProductImageEntity img : image) {
            img.setDeletedAt(purgeAt);
        }
        if (remaining <= 1) {
            product.setDeletedAt(purgeAt);   // last variant, product-um
        }
        return new ApiResponseMsg("Variant deleted.");
    }

//  ============================== Common Methods for Slug and Sku ==============================

    public String generateSlug(String name){
        return name.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .trim()
                .replaceAll("\\s+", "-");
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

    private void validatePricing(BigDecimal price, BigDecimal discountPrice, Integer stock) {
        if (price == null || price.signum() <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
        if (stock == null || stock < 0) {
            throw new IllegalArgumentException("Stock quantity must be 0 or more");
        }
        if (discountPrice != null) {
            if (discountPrice.signum() <= 0) {
                throw new IllegalArgumentException("Offer price must be greater than 0");
            }
            if (discountPrice.compareTo(price) > 0) {
                throw new IllegalArgumentException("Offer price cannot be greater than price");
            }
        }
    }

}
