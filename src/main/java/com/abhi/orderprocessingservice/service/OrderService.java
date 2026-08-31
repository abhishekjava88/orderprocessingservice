package com.abhi.orderprocessingservice.service;

import com.abhi.orderprocessingservice.repository.OrderRepository;
import com.abhi.orderprocessingservice.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order createOrder(Order order){
        return orderRepository.save(order);
    }

    public Order updateOrder(Order dbOrder, Order orderToUpdate){
        dbOrder.setAmount(orderToUpdate.getAmount() !=null?orderToUpdate.getAmount():dbOrder.getAmount()) ;
        dbOrder.setStatus(orderToUpdate.getStatus() !=null?orderToUpdate.getStatus():dbOrder.getStatus());
        dbOrder.setCustomerName(orderToUpdate.getCustomerName() !=null?orderToUpdate.getCustomerName():dbOrder.getCustomerName());
        dbOrder.setUpdatedAt(Instant.now());
        return orderRepository.save(dbOrder);
    }

    public Optional<Order> findOrderById(Long orderId){
        return orderRepository.findById(orderId);
    }

    public void deleteOrder(Order order){
       orderRepository.delete(order);
    }

}
