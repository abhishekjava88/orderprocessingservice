package com.abhi.orderprocessingservice.controller;

import com.abhi.orderprocessingservice.model.CreateOrderRequest;
import com.abhi.orderprocessingservice.model.Order;
import com.abhi.orderprocessingservice.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;


import java.math.BigDecimal;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;


    @Test
    void createOrderAndReturnResponse() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Order order = new Order();
        order.setCustomerName("Abhishek");
        order.setAmount(BigDecimal.valueOf(78L));
        when(orderService.createOrder(any(Order.class)))
                .thenReturn(order);

        CreateOrderRequest createOrderRequest = new CreateOrderRequest("Abhishek", BigDecimal.valueOf(78L));
              mockMvc.perform(post("/order/createOrder")
                              .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createOrderRequest)))
                .andExpect(status().isOk());
    }

}