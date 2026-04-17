package com.foodlink.orders.domain.repository;

import com.foodlink.orders.domain.entity.Order;
import java.util.Optional;

public interface OrderRepository {

  Optional<Order> findById(Long orderId);

  Order save(Order order);
}
