package com.abhi.orderprocessingservice.controller;

import com.abhi.orderprocessingservice.model.CreateOrderRequest;
import com.abhi.orderprocessingservice.model.Order;
import com.abhi.orderprocessingservice.model.UpdateOrderRequest;
import com.abhi.orderprocessingservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/createOrder")
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest request){
        Order order = new Order();
        order.setCustomerName(request.customerName());
        order.setAmount(request.amount());
        Order result = orderService.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/findOrder/{orderId}")
    public ResponseEntity<Order> findOrder(@PathVariable Long orderId){
        Order result = orderService.findOrderById(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PatchMapping("/updateOrder/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long orderId, @RequestBody UpdateOrderRequest order){
        Order result = orderService.updateOrder(orderId,order);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/deleteOrder/{orderId}")
    public ResponseEntity<Order> deleteOrder(@PathVariable Long orderId){
        Order deletedOrder = orderService.deleteOrder(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(deletedOrder);
    }


}
