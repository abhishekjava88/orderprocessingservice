package com.abhi.orderprocessingservice.model;

import java.math.BigDecimal;

public record CreateOrderRequest(String customerName, BigDecimal amount) {
}
