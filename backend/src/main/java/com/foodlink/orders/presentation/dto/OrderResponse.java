package com.foodlink.orders.presentation.dto;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponse(
    Long id,
    String status,
    BigDecimal totalPrice,
    List<OrderItemResponse> items) {
}
