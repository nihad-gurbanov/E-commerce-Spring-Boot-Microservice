package com.holbie.microservices.order.controller;

import com.holbie.microservices.order.dto.OrderRequestDto;
import com.holbie.microservices.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequestDto orderRequestDto) {
        orderService.placeOrder(orderRequestDto);
        return "Order placed successfully";
    }

}
