package com.abhi.orderprocessingservice.model;

import java.math.BigDecimal;
import java.time.Instant;

public record OrderResponse(Long id, String customerName, OrderStatus status, BigDecimal amount, Instant createdAt, Instant updatedAt) {
    public static OrderResponse from(Order order) {
        return new OrderResponse(order.getId(), order.getCustomerName(), order.getStatus(), order.getAmount(), order.getCreatedAt(), order.getUpdatedAt());
    }
}
