package com.example.backend.controller;

import com.example.backend.dto.OrderDto;
import com.example.backend.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/orders")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

    //create an order
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@PathVariable Long userId, @RequestBody OrderDto orderDto) {
        OrderDto savedOrder = orderService.createOrder(userId, orderDto);
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }

    //get an order detail
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long orderId) {
        OrderDto order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    //get all orders of an user
    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders(@PathVariable Long userId) {
        List<OrderDto> orders = orderService.getAllOrders(userId);
        return ResponseEntity.ok(orders);
    }

    //update an order
    @PostMapping("/{orderId}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long orderId, @RequestBody OrderDto orderDto) {
        OrderDto order = orderService.updateOrder(orderId, orderDto);
        return ResponseEntity.ok(order);
    }

    //delete an order
    @DeleteMapping("{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok("Deleted Successfully");
    }
}
