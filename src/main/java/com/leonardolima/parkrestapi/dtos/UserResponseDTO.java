package com.leonardolima.parkrestapi.dtos;

import com.leonardolima.parkrestapi.entities.User.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private Role role;
}
