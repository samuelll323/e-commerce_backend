package com.example.backend.service;

import com.example.backend.dto.OrderDto;

import java.util.List;

public interface OrderService {
    // Create an order
    OrderDto createOrder(Long userId, OrderDto orderDto);

    // Get an order detail
    OrderDto getOrderById(Long orderId);

    // Get all orders
    List<OrderDto> getAllOrders(Long userId);

    // Update an order
    OrderDto updateOrder(Long orderId, OrderDto orderDto);

    // Delete an order
    void deleteOrder(Long orderId);
}
