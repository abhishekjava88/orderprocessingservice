package com.abhi.orderprocessingservice.controller;

import com.abhi.orderprocessingservice.model.Order;
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
    public ResponseEntity<Order> createOrder(@RequestBody Order order){
        Order result = orderService.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/findOrder/{orderId}")
    public ResponseEntity<Order> findOrder(@PathVariable Long orderId){
        Optional<Order> result = orderService.findOrderById(orderId);
        if(result.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(result.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PatchMapping("/updateOrder")
    public ResponseEntity<Order> updateOrder(@RequestBody Order order){
        Optional<Order> result = orderService.findOrderById(order.getId());
        if(result.isPresent()){
            Order dbOrder = result.get();
            return ResponseEntity.status(HttpStatus.OK).body(orderService.updateOrder(dbOrder,order));
        }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/deleteOrder/{orderId}")
    public ResponseEntity<Order> deleteOrder(@PathVariable Long orderId){
        Optional<Order> result = orderService.findOrderById(orderId);
        if(result.isPresent()){
            Order order = result.get();
            orderService.deleteOrder(order);
            return ResponseEntity.status(HttpStatus.OK).body(order);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


}
