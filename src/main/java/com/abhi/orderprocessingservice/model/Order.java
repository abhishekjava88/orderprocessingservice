package com.abhi.orderprocessingservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {

    public Order(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String customerName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    private Instant updatedAt;

    @PrePersist
    protected void onCreate(){
        status = OrderStatus.PENDING;
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate(){
        updatedAt = Instant.now();
    }

}
