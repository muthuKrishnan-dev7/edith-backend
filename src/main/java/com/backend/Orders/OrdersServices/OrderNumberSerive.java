package com.backend.Orders.OrdersServices;

public class OrderNumberSerive {

    public String generateOrderNumber(Long storeId, Long userId, Long orderId){
        return "ORD-" + storeId
                + "-" + userId
                + "-" + orderId;
    }

}
