package com.abhi.orderprocessingservice.repository;

import com.abhi.orderprocessingservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
