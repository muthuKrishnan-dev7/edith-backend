package com.backend.Orders.OrderItems;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemsRepo extends JpaRepository<OrderItemsEntity, Long> {

    List<OrderItemsEntity> findByOrder_Id(Long orderId);

}
