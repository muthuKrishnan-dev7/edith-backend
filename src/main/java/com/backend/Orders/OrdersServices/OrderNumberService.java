package com.backend.Orders.OrdersServices;

import org.springframework.stereotype.Service;

@Service
public class OrderNumberService {

    public String generateOrderNumber(Long storeId, Long userId, Long orderId){
        return "ORD-" + storeId
                + "-" + userId
                + "-" + orderId;
    }

}
