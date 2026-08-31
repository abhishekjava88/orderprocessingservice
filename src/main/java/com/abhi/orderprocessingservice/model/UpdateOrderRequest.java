package com.abhi.orderprocessingservice.model;

import java.math.BigDecimal;

public record UpdateOrderRequest(String customerName,OrderStatus status, BigDecimal amount){
}
