package com.foodlink.orders.presentation.error;

public class OrderNotFoundException extends RuntimeException {

  public OrderNotFoundException(Long orderId) {
    super("Order with id " + orderId + " not found");
  }
}
