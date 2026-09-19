package com.abhi.orderprocessingservice.service;

import com.abhi.orderprocessingservice.exception.OrderNotFoundException;
import com.abhi.orderprocessingservice.model.Order;
import com.abhi.orderprocessingservice.model.OrderStatus;
import com.abhi.orderprocessingservice.model.UpdateOrderRequest;
import com.abhi.orderprocessingservice.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static com.abhi.orderprocessingservice.model.OrderStatus.PENDING;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setCustomerName("Abhishek");
        order.setAmount(BigDecimal.valueOf(22.0));
    }

    @Test
    void createOrder_savesAndReturnsOrder() {
        when(orderRepository.save(order)).thenReturn(order);
        Order result = orderService.createOrder(order);
        verify(orderRepository).save(order);
        assertEquals(order,result);

    }

    @Test
    void updateOrder_throwsWhenNotFound() {
        UpdateOrderRequest updateOrderRequest = new UpdateOrderRequest("Abhishek",PENDING,BigDecimal.valueOf(56.0));
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class,()->orderService.updateOrder(anyLong(),updateOrderRequest));
    }

    @Test
    void updateOrder_onlyUpdatesNonNullFields() {
        UpdateOrderRequest updateOrderRequest = new UpdateOrderRequest(null,null,BigDecimal.valueOf(52.0));
        Order order = new Order();
        order.setCustomerName("Abhishek");
        order.setAmount(BigDecimal.valueOf(22.0));
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);
        Order result = orderService.updateOrder(1L,updateOrderRequest);
        assertEquals(result.getAmount(),updateOrderRequest.amount());
        assertNotNull(result.getAmount());
        assertEquals("Abhishek", result.getCustomerName());
    }

    @Test
    void findOrderById_throwsWhenMissing() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class,()-> orderService.findOrderById(anyLong()));
    }

    @Test
    void deleteOrder() {
        Order actualOrder = new Order();
        actualOrder.setCustomerName("Abhishek");
        actualOrder.setAmount(BigDecimal.valueOf(22.0));
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(actualOrder));
        Order result = orderService.deleteOrder(2L);
        assertEquals(actualOrder,result);
    }
}