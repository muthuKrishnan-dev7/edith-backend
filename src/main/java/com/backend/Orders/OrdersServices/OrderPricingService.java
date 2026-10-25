package com.backend.Orders.OrdersServices;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderPricingService {

    public BigDecimal getSubTotal(
            BigDecimal unitPrice,
            Integer quantity) {

        return unitPrice.multiply(
                BigDecimal.valueOf(quantity)
        );
    }

    public BigDecimal getDiscountTotal(
            BigDecimal discountPrice,
            Integer quantity){
        return discountPrice.multiply(BigDecimal.valueOf(quantity));
    }

//    ===== Add proper tax and discount verify later --------
    public BigDecimal getLineTotalOfOrder(
            BigDecimal discountTotal
    ){
        return discountTotal;
    }

//    ===== For OrderItems_Table =====
    public BigDecimal getLineTotalOfItems(
            BigDecimal unitPrice,
            Integer quantity,
            BigDecimal discountPrice){
        return discountPrice.multiply(BigDecimal.valueOf(quantity));
    }

}
