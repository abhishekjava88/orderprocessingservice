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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Executable;
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
    void setUp(){
        order = new Order();
        order.setCustomerName("Abhishek");
        order.setStatus(PENDING);
        order.setAmount(BigDecimal.valueOf(75L));
    }

@Test
    void createOrder_savesAndReturnsOrder(){
        when(orderRepository.save(order)).thenReturn(order);
        Order result = orderService.createOrder(order);
        assertEquals(order,result);
    }

    @Test
    void updateOrder_throwsWhenNotFound(){
        UpdateOrderRequest updateOrderRequest = new UpdateOrderRequest("Abhishek",PENDING,BigDecimal.valueOf(78L));
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class, ()->orderService.updateOrder(10L,updateOrderRequest));
    }

    @Test
    void updateOrder_onlyUpdatesNonNullFields(){
        UpdateOrderRequest updateOrderRequest = new UpdateOrderRequest(null,null,BigDecimal.valueOf(85L));
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);
        Order result = orderService.updateOrder(10L,updateOrderRequest);
        assertEquals(result.getAmount(),updateOrderRequest.amount());
        assertNotNull(result.getStatus());
        assertNotNull(result.getCustomerName());
        assertEquals("Abhishek", result.getCustomerName());
    }

    @Test
    void findOrderById_throwsWhenMissing(){
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class,()-> orderService.findOrderById(10L));
    }

    @Test
    void deleteOrder_deletesAndReturnsOrder(){
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        Order result = orderService.deleteOrder(10L);
        assertEquals(result,order);
        verify(orderRepository).delete(order);
    }
}