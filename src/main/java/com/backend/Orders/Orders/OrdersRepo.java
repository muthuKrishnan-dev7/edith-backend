package com.backend.Orders.Orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrdersRepo extends JpaRepository<OrdersEntity, Long> {

    Optional<OrdersEntity> findByIdAndStore_IdAndUser_Id(Long orderId, Long storeId, Long userId);

}
