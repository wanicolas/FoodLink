package com.foodlink.orders.application.service;

import com.foodlink.orders.domain.entity.Order;
import com.foodlink.orders.domain.entity.OrderItem;
import com.foodlink.orders.domain.repository.OrderRepository;
import com.foodlink.orders.presentation.dto.OrderItemResponse;
import com.foodlink.orders.presentation.dto.OrderResponse;
import com.foodlink.orders.presentation.error.OrderNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

  private final OrderRepository orderRepository;

  public OrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public OrderResponse getOrderById(Long orderId) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new OrderNotFoundException(orderId));

    order.recalculateTotalPrice();

    List<OrderItemResponse> itemResponses = order.getItems().stream()
        .map(this::toItemResponse)
        .toList();

    return new OrderResponse(
        order.getId(),
        order.getStatus().name(),
        order.getTotalPrice(),
        itemResponses);
  }

  private OrderItemResponse toItemResponse(OrderItem item) {
    return new OrderItemResponse(
        item.getName(),
        item.getPrice(),
        item.getQuantity());
  }
}
