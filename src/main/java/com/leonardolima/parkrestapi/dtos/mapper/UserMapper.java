package com.leonardolima.parkrestapi.dtos.mapper;

import org.modelmapper.ModelMapper;

import com.leonardolima.parkrestapi.dtos.UserCreateDTO;
import com.leonardolima.parkrestapi.dtos.UserResponseDTO;
import com.leonardolima.parkrestapi.entities.User;

public class UserMapper {
    
    public static User toUser(UserCreateDTO userCreateDTO) {
        return new ModelMapper().map(userCreateDTO, User.class);
    }

    public static UserResponseDTO toDTO(User user) {
        return new ModelMapper().map(user, UserResponseDTO.class);
    }
}
