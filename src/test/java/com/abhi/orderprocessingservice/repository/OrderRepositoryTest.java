package com.abhi.orderprocessingservice.repository;

import com.abhi.orderprocessingservice.model.Order;
import com.abhi.orderprocessingservice.model.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    private Order order;

    @BeforeEach
    void setUp(){
        order = new Order();
        order.setCustomerName("Abhishek");
        order.setAmount(BigDecimal.valueOf(78L));
    }

    @Test
    void save_setsPendingStatusAndTimestampsViaPrePersist(){
        Order result = orderRepository.save(order);
        assertTrue(result.getStatus() == OrderStatus.PENDING);
        assertNotNull(result.getUpdatedAt());
        assertNotNull(result.getCreatedAt());
    }

    @Test
    void save_thenModifyAndSaveAgain_updatesUpdatedAtViaPreUpdate() throws InterruptedException {
        Order result = orderRepository.save(order);
        orderRepository.flush();
        Instant updatedTime = result.getUpdatedAt();
        Thread.sleep(5000);
        result.setAmount(BigDecimal.valueOf(52L));
        Order updatedResult = orderRepository.save(result);
        orderRepository.flush();
        assertNotEquals(updatedResult.getUpdatedAt(),updatedTime);
    }

    @Test
    void save_withNullCustomerName_throwsConstraintViolation(){
        order.setCustomerName(null);
        assertThrows(DataIntegrityViolationException.class,()->orderRepository.save(order));
    }

    @Test
    void findById_returnsEmptyForNonExistentId(){
        Optional<Order> result = orderRepository.findById(10L);
        assertTrue(result.isEmpty());
    }
}