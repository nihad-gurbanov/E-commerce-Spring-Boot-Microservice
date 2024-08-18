package com.holbie.microservices.order.service;

import com.holbie.microservices.order.client.InventoryClient;
import com.holbie.microservices.order.dto.OrderRequestDto;
import com.holbie.microservices.order.event.OrderPlacedEvent;
import com.holbie.microservices.order.model.Order;
import com.holbie.microservices.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public void placeOrder(OrderRequestDto orderRequestDto) {
        var isProductInStock = inventoryClient.isInStock(orderRequestDto.getSkuCode(), orderRequestDto.getQuantity());

        if (!isProductInStock) {
            throw new RuntimeException("Product is out of stock");
        }

        Order order = modelMapper.map(orderRequestDto, Order.class);
        order.setOrderNumber(UUID.randomUUID().toString());
        orderRepository.save(order);

        OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent();
        orderPlacedEvent.setOrderNumber(order.getOrderNumber());
        orderPlacedEvent.setEmail(orderRequestDto.getUserDetails().getEmail());



        log.info("Start- Sending OrderPlacedEvent {} to Kafka Topic", orderPlacedEvent);
        kafkaTemplate.send("order-placed", orderPlacedEvent);
        log.info("End- Sending OrderPlacedEvent {} to Kafka Topic", orderPlacedEvent);
    }
}
