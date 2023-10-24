package com.leonardolima.parkrestapi.dtos.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.leonardolima.parkrestapi.dtos.UserCreateDTO;
import com.leonardolima.parkrestapi.dtos.UserResponseDTO;
import com.leonardolima.parkrestapi.entities.User;

public class UserMapper {
    
    public static User toCreateUser(UserCreateDTO userCreateDTO) {
        return new ModelMapper().map(userCreateDTO, User.class);
    }

    public static UserResponseDTO toResponseUser(User user) {
        return new ModelMapper().map(user, UserResponseDTO.class);
    }

    public static List<UserResponseDTO> toListUser(List<User> users) {
        return users.stream().map(user -> toResponseUser(user)).collect(Collectors.toList());
    }
}
