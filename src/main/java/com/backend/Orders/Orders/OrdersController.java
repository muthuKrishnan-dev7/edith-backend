package com.backend.Orders.Orders;

import com.backend.Global_DTOs.ApiResponseMsg;
import com.backend.Orders.DTOs.RequestOrder.RequestOrder;
import com.backend.Orders.Orders.DTOs.ResponseCreateOrder;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrdersController {

    private final OrdersService serv;

    public OrdersController(OrdersService serv) {
        this.serv = serv;
    }

    @PostMapping("/create-order")
    public ResponseEntity<ResponseCreateOrder> createOrder(@RequestBody @Valid RequestOrder request){
        ResponseCreateOrder response = serv.createOrder(request);
        return ResponseEntity.ok().body(response);
    }

}
