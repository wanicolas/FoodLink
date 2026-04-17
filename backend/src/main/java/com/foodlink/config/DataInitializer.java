package com.foodlink.config;

import com.foodlink.orders.domain.entity.Order;
import com.foodlink.orders.domain.entity.OrderItem;
import com.foodlink.orders.domain.model.OrderStatus;
import com.foodlink.orders.domain.repository.OrderRepository;
import java.math.BigDecimal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

  private final OrderRepository orderRepository;

  public DataInitializer(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @Override
  public void run(String... args) {
    if (orderRepository.findById(1L).isPresent()) {
      return;
    }

    Order order = new Order(1L, OrderStatus.EN_PREPARATION);
    order.addItem(new OrderItem("Burger Maison", new BigDecimal("12.50"), 1));
    order.addItem(new OrderItem("Frites Maison", new BigDecimal("4.00"), 2));
    order.addItem(new OrderItem("Limonade", new BigDecimal("3.50"), 1));
    order.recalculateTotalPrice();

    orderRepository.save(order);
  }
}
