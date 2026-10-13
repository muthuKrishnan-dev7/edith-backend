package com.backend.Payments;

import com.backend.Exception.Custom.OrderNotFoundException;
import com.backend.Exception.Custom.StoreNotFoundException;
import com.backend.Exception.Custom.UserNotFoundException;
import com.backend.Orders.OrderItems.OrderItemsRepo;
import com.backend.Orders.Orders.OrdersEntity;
import com.backend.Orders.Orders.OrdersRepo;
import com.backend.Payments.DTOs.RequestCreatePayment;
import com.backend.Payments.DTOs.ResponsePayment;
import com.backend.Stores.Store.StoreEntity;
import com.backend.Stores.Store.StoreRepo;
import com.backend.TenantResolve.TenantContext;
import com.backend.Users.User.UserEntity;
import com.backend.Users.User.UserRepo;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class PaymentService {

    private final PaymentRepo paymentRepo;
    private final UserRepo userRepo;
    private final StoreRepo storeRepo;
    private final OrdersRepo ordersRepo;
    private final OrderItemsRepo orderItemsRepo;

    public PaymentService(PaymentRepo paymentRepo, UserRepo userRepo, StoreRepo storeRepo, OrdersRepo ordersRepo, OrderItemsRepo orderItemsRepo) {
        this.paymentRepo = paymentRepo;
        this.userRepo = userRepo;
        this.storeRepo = storeRepo;
        this.ordersRepo = ordersRepo;
        this.orderItemsRepo = orderItemsRepo;
    }

    public ResponsePayment createPayment(@Valid RequestCreatePayment request){
        Long storeId = TenantContext.getCurrentTenantId();
        StoreEntity store = storeRepo.findById(storeId).orElseThrow(StoreNotFoundException::new);
        Long userId = (Long) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        UserEntity user = userRepo.findById(userId).orElseThrow(UserNotFoundException::new);
        OrdersEntity order = ordersRepo.findById(request.orderId()).orElseThrow(OrderNotFoundException::new);

    }

}
