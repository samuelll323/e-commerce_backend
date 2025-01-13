package com.example.backend.mapper;

import com.example.backend.dto.OrderDto;
import com.example.backend.model.Order;
import com.example.backend.model.Product;
import com.example.backend.model.User;

import java.sql.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderDto mapToOrderDto(Order order) {
        return new OrderDto(
                order.getId(),
                order.getUser().getId(),
                order.getCreatedAt(),
                order.getTotalPrice(),
                order.getProducts().stream()
                        .map(product -> product.getId())
                        .collect(Collectors.toList())
        );
    }

    public static Order mapToOrder(OrderDto orderDto, User user, List<Product> products) {
        Order order = new Order();
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setTotalPrice(orderDto.getTotalPrice());
        order.setProducts(new ArrayList<>(products));
        return order;
    }
}
