package com.abhi.orderprocessingservice.service;

import com.abhi.orderprocessingservice.exception.OrderNotFoundException;
import com.abhi.orderprocessingservice.model.UpdateOrderRequest;
import com.abhi.orderprocessingservice.repository.OrderRepository;
import com.abhi.orderprocessingservice.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order createOrder(Order order){
        return orderRepository.save(order);
    }

    @Transactional
    public Order updateOrder(Long orderId, UpdateOrderRequest orderToUpdate) throws OrderNotFoundException{
        Order dbOrder = findOrderById(orderId);
        if (orderToUpdate.customerName() != null) dbOrder.setCustomerName(orderToUpdate.customerName());
        if (orderToUpdate.status() != null) dbOrder.setStatus(orderToUpdate.status());
        if (orderToUpdate.amount() != null) dbOrder.setAmount(orderToUpdate.amount());
        return orderRepository.save(dbOrder);
    }

    public Order findOrderById(Long orderId){
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    @Transactional
    public Order deleteOrder(Long orderId){
       Order dbOrder = findOrderById(orderId);
       orderRepository.delete(dbOrder);
       return dbOrder;
    }

}
