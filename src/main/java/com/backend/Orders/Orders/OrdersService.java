package com.backend.Orders.Orders;

import com.backend.Exception.Custom.Product.ProductNotFoundException;
import com.backend.Exception.Custom.Product.ProductVariantNotFoundException;
import com.backend.Exception.Custom.Store.StoreNotFoundException;
import com.backend.Exception.Custom.User.UserNotFoundException;
import com.backend.Orders.DTOs.RequestOrder.RequestOrder;
import com.backend.Orders.DTOs.RequestOrder.RequestOrderItems;
import com.backend.Orders.OrderItems.OrderItemsEntity;
import com.backend.Orders.OrderItems.OrderItemsRepo;
import com.backend.Orders.Orders.DTOs.ResponseCreateOrder;
import com.backend.Orders.OrdersServices.OrderNumberService;
import com.backend.Orders.OrdersServices.OrderPricingService;
import com.backend.Products.Core.Product.ProductEntity;
import com.backend.Products.Core.Product.ProductRepo;
import com.backend.Products.Core.ProductVariant.ProductVariantEntity;
import com.backend.Products.Core.ProductVariant.ProductVariantRepo;
import com.backend.Stores.Store.StoreEntity;
import com.backend.Stores.Store.StoreRepo;
import com.backend.Core.TenantResolve.TenantContext;
import com.backend.Shopping.Address.AddressLabel;
import com.backend.Shopping.Address.UserAddressEntity;
import com.backend.Shopping.Address.UserAddressRepo;
import com.backend.Users.User.UserEntity;
import com.backend.Users.User.UserRepo;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrdersService {

    private final OrdersRepo orderRepo;
    private final UserRepo userRepo;
    private final StoreRepo storeRepo;
    private final UserAddressRepo addressRepo;
    private final ProductRepo productRepo;
    private final ProductVariantRepo productVariantRepo;
    private final OrderNumberService orderNumberSerive;
    private final OrderPricingService orderPricingService;
    private final OrderItemsRepo orderItemsRepo;

    public OrdersService(OrdersRepo orderRepo, UserRepo userRepo, StoreRepo storeRepo, UserAddressRepo addressRepo, ProductRepo productRepo, ProductVariantRepo productVariantRepo, OrderNumberService orderNumberSerive1, OrderPricingService orderPricingService, OrderItemsRepo orderItemsRepo) {
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
        this.storeRepo = storeRepo;
        this.addressRepo = addressRepo;
        this.productRepo = productRepo;
        this.productVariantRepo = productVariantRepo;
        this.orderNumberSerive = orderNumberSerive1;
        this.orderPricingService = orderPricingService;
        this.orderItemsRepo = orderItemsRepo;
    }

    public ResponseCreateOrder createOrder(@Valid RequestOrder request) {
        Long storeId = TenantContext.getCurrentTenantId();
        StoreEntity store = storeRepo.findById(storeId).orElseThrow(StoreNotFoundException::new);
        Long userId = (Long) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        UserEntity user = userRepo.findById(userId).orElseThrow(UserNotFoundException::new);

//        ===== Create Order =====
        OrdersEntity newOrder = new OrdersEntity();
        newOrder.setStore(store);
        newOrder.setUser(user);
//        ===== Address =====
        if (request.addressId() != null) {
            UserAddressEntity userAddress = addressRepo.findById(request.addressId()).orElseThrow(() -> new RuntimeException("Address not found."));
            newOrder.setAddress(userAddress);
            newOrder.setAddressLabel(userAddress.getLabel());
            newOrder.setRecipientName(userAddress.getName());
            newOrder.setPhone(userAddress.getPhone());
            newOrder.setAlternatePhone(userAddress.getAlternatePhone());
            newOrder.setEmail(userAddress.getEmail());
            newOrder.setAddressLine1(userAddress.getAddressLine1());
            newOrder.setAddressLine2(userAddress.getAddressLine2());
            newOrder.setCity(userAddress.getCity());
            newOrder.setState(userAddress.getState());
            newOrder.setPincode(userAddress.getPincode());
            newOrder.setCountry(userAddress.getCountry());
        } else {
            newOrder.setAddress(null);
            newOrder.setAddressLabel(
                    AddressLabel.valueOf(
                            request.address().addressLabel().toUpperCase()
                    )
            );
            newOrder.setRecipientName(request.address().recipientName());
            newOrder.setPhone(request.address().phone());
            newOrder.setAlternatePhone(request.address().alternatePhone());
            newOrder.setEmail(request.address().email());
            newOrder.setAddressLine1(request.address().addressLine1());
            newOrder.setAddressLine2(request.address().addressLine2());
            newOrder.setCity(request.address().city());
            newOrder.setState(request.address().state());
            newOrder.setPincode(request.address().pincode());
            newOrder.setCountry(request.address().country());
        }

//        ===== Save & Get Order Id =====
        OrdersEntity order = orderRepo.save(newOrder);
        Long orderId = order.getId();

//        ===== Process of OrderItems add =====
        List<OrderItemsEntity> orderItems = new ArrayList<>();
        for (RequestOrderItems item : request.items()) {

            ProductEntity product = productRepo.findById(item.productId())
                    .orElseThrow(ProductNotFoundException::new);
            ProductVariantEntity variant = productVariantRepo.findById(item.variantId())
                    .orElseThrow(ProductVariantNotFoundException::new);

            if (!variant.getProduct().getId().equals(product.getId())) {
                throw new IllegalArgumentException(
                        "Variant does not belong to the selected product"
                );
            }

            OrderItemsEntity orderItem = new OrderItemsEntity();

            orderItem.setStore(store);
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setProductVariant(variant);
            orderItem.setProductName(product.getName());
            orderItem.setSku(variant.getSku());

            // Pricing snapshot
            orderItem.setUnitPrice(variant.getPrice());
            orderItem.setQuantity(item.quantity());
            orderItem.setDiscountTotal(variant.getDiscount_price());

            BigDecimal lineTotal =
                    orderPricingService.getLineTotalOfItems(
                            variant.getPrice(),
                            item.quantity(),
                            variant.getDiscount_price()
                    );
            orderItem.setLineTotal(lineTotal);
            orderItems.add(orderItem);
        }
        orderItemsRepo.saveAll(orderItems);

//        ===== Process of Get amount of Order =====
        BigDecimal subTotal = BigDecimal.ZERO;
        BigDecimal discountTotal = BigDecimal.ZERO;
//        BigDecimal taxTotal = BigDecimal.ZERO;
//        BigDecimal shippingAmount = BigDecimal.ZERO;
        for (OrderItemsEntity orderedItem : orderItems) {
            BigDecimal itemSubTotal =
                    orderPricingService.getSubTotal(
                            orderedItem.getUnitPrice(),
                            orderedItem.getQuantity()
                    );
            subTotal = subTotal.add(itemSubTotal);

            BigDecimal itemDiscountTotal =
                    orderPricingService.getDiscountTotal(
                            orderedItem.getDiscountTotal(),
                            orderedItem.getQuantity()
                    );
            discountTotal = discountTotal.add(itemDiscountTotal);
        }

        BigDecimal lineTotal = orderPricingService.getLineTotalOfOrder(discountTotal);

        String orderNumber = orderNumberSerive.generateOrderNumber(storeId, userId, orderId);
        order.setOrderNumber(orderNumber);
        order.setSubTotal(subTotal);
        order.setDiscountTotal(discountTotal);
        order.setLineTotal(lineTotal);
        orderRepo.save(order);

        return new ResponseCreateOrder(
                orderId,
                orderNumber,
                order.getPaymentStatus().toString(),
                order.getLineTotal()
        );
    }


} // ===== End =====