package com.backend.Payments;

import com.backend.Exception.Custom.*;
import com.backend.Orders.Enums.OrderStatus;
import com.backend.Orders.Enums.PaymentStatus;
import com.backend.Orders.OrderItems.OrderItemsRepo;
import com.backend.Orders.Orders.OrdersEntity;
import com.backend.Orders.Orders.OrdersRepo;
import com.backend.Payments.DTOs.RequestCreatePayment;
import com.backend.Payments.DTOs.ResponsePayment;
import com.backend.Stores.Store.StoreEntity;
import com.backend.Stores.Store.StoreRepo;
import com.backend.Stores.StorePaymentSetting.StorePaymentEntity;
import com.backend.Stores.StorePaymentSetting.StorePaymentRepo;
import com.backend.TenantResolve.TenantContext;
import com.backend.Users.User.UserEntity;
import com.backend.Users.User.UserRepo;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.json.JSONObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService {

    private final PaymentRepo paymentRepo;
    private final UserRepo userRepo;
    private final StoreRepo storeRepo;
    private final OrdersRepo ordersRepo;
    private final OrderItemsRepo orderItemsRepo;
    private final StorePaymentRepo storePaymentRepo;

    public PaymentService(PaymentRepo paymentRepo, UserRepo userRepo, StoreRepo storeRepo, OrdersRepo ordersRepo, OrderItemsRepo orderItemsRepo, StorePaymentRepo storePaymentRepo) {
        this.paymentRepo = paymentRepo;
        this.userRepo = userRepo;
        this.storeRepo = storeRepo;
        this.ordersRepo = ordersRepo;
        this.orderItemsRepo = orderItemsRepo;
        this.storePaymentRepo = storePaymentRepo;
    }

    @Transactional
    public ResponsePayment getPaymentDetails(@Valid RequestCreatePayment request) throws RazorpayException {
        Long storeId = TenantContext.getCurrentTenantId();
        Long userId = (Long) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        OrdersEntity order = ordersRepo.findByIdAndStore_IdAndUser_Id(request.orderId(), storeId, userId)
                .orElseThrow(OrderNotFoundException::new);

        StorePaymentEntity storePayment = storePaymentRepo.findByStore_Id(storeId)
                .orElseThrow(StorePaymentNotFound::new);

        if (order.getPaymentStatus() == PaymentStatus.PAID) {
            throw new OrderAlreadyPaidException();
        }

        // rupees -> paise
        BigDecimal amountInPaise = order.getLineTotal().multiply(BigDecimal.valueOf(100));

        RazorpayClient client = new RazorpayClient(
                storePayment.getPaymentId(),
                storePayment.getPaymentSecret()
        );

        JSONObject options = new JSONObject();
        options.put("amount", amountInPaise.longValue());
        options.put("currency", "INR");
        options.put("receipt", "order_" + order.getId());

        com.razorpay.Order rpOrder = client.orders.create(options);
        String razorpayOrderId = rpOrder.get("id");

        return new ResponsePayment(
                order.getId(),
                razorpayOrderId,
                storePayment.getPaymentId(),
                amountInPaise,
                "INR"
        );
    }

}
