package com.example.backend.mapper;

import com.example.backend.dto.UserDto;
import com.example.backend.model.Order;
import com.example.backend.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserDto mapToUserDto(User user) {
        return new UserDto (
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getAddress(),
                user.getOrders() != null
                        ? user.getOrders().stream()
                        .map(OrderMapper::mapToOrderDto)
                        .collect(Collectors.toList())
                        : Collections.emptyList()
        );
    }

    public static User mapToUser(UserDto userDto, List<Order> orders) {
        return new User(
                userDto.getId(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmail(),
                userDto.getPhoneNumber(),
                userDto.getAddress(),
                orders
        );
    }
}
