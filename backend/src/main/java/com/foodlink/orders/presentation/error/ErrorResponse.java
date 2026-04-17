package com.foodlink.orders.presentation.error;

import java.time.Instant;

public record ErrorResponse(
    String message,
    Instant timestamp) {
}
