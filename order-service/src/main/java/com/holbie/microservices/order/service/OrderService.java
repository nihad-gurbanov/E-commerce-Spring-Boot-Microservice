package com.holbie.microservices.order.service;

import com.holbie.microservices.order.client.InventoryClient;
import com.holbie.microservices.order.dto.OrderRequestDto;
import com.holbie.microservices.order.dto.OrderResponseDto;
import com.holbie.microservices.order.model.Order;
import com.holbie.microservices.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final InventoryClient inventoryClient;

    public void placeOrder(OrderRequestDto orderRequestDto) {
        var isProductInStock = inventoryClient.isInStock(orderRequestDto.getSkuCode(), orderRequestDto.getQuantity());

        if (!isProductInStock) {
            throw new RuntimeException("Product is out of stock");
        }

        Order order = modelMapper.map(orderRequestDto, Order.class);
        order.setOrderNumber(UUID.randomUUID().toString());
        orderRepository.save(order);
    }
}
