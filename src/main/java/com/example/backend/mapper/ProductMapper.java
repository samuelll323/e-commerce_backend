package com.example.backend.mapper;

import com.example.backend.dto.OrderDto;
import com.example.backend.dto.ProductDto;
import com.example.backend.model.Order;
import com.example.backend.model.Product;
import com.example.backend.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductMapper {
    public static ProductDto mapToProductDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getOrders().stream()
                        .map(order -> order.getId())
                        .collect(Collectors.toSet())
        );
    }

    public static Product mapToProduct(ProductDto productDto, Set<Order> orders) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());
        product.setOrders(new HashSet<>(orders));
        return product;
    }
}
