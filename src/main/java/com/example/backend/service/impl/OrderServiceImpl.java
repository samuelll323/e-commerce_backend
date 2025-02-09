package com.example.backend.service.impl;

import com.example.backend.dto.OrderDto;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.mapper.OrderMapper;
import com.example.backend.model.Order;
import com.example.backend.model.Product;
import com.example.backend.model.User;
import com.example.backend.repository.OrderRepository;
import com.example.backend.repository.ProductRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.OrderService;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;

    @Override
    public OrderDto createOrder(Long userId, OrderDto orderDto) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );
        if (orderDto.getProductIds() == null || orderDto.getProductIds().isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one product.");
        }

        List<Product> products = productRepository.findAllById(orderDto.getProductIds());

        List<Product> finalProducts = new ArrayList<>();
        for (Long id : orderDto.getProductIds()) {
            for (Product p : products) {
                if (p.getId().equals(id)) {
                    finalProducts.add(p);
                }
            }
        }

        BigDecimal totalPrice = finalProducts.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = OrderMapper.mapToOrder(orderDto, user, products);
        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(order);

        for (Product product : products) {
            product.getOrders().add(savedOrder);
            productRepository.save(product);
        }

        return OrderMapper.mapToOrderDto(savedOrder);
    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("No such order exist")
                );
        return OrderMapper.mapToOrderDto(order);
    }

    @Override
    public List<OrderDto> getAllOrders(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("No such user exist")
        );
        List<Order> orders = orderRepository.findAllByUserId(user.getId());
        return orders.stream()
                .map(OrderMapper::mapToOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto updateOrder(Long orderId, OrderDto orderDto) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new ResourceNotFoundException("No such order exist")
        );
        order.setCreatedAt(LocalDateTime.now());
        List<Product> products = productRepository.findAllById(orderDto.getProductIds());
        order.setProducts(new ArrayList<>(products));
        order.setTotalPrice(orderDto.getTotalPrice());
        Order savedOrder = orderRepository.save(order);
        return OrderMapper.mapToOrderDto(savedOrder);
    }

    @Override
    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new ResourceNotFoundException("No such order exist")
        );
        orderRepository.deleteById(orderId);
    }
}
