package com.leonardolima.parkrestapi.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leonardolima.parkrestapi.dtos.UpdatePasswordDTO;
import com.leonardolima.parkrestapi.dtos.UserCreateDTO;
import com.leonardolima.parkrestapi.dtos.UserResponseDTO;
import com.leonardolima.parkrestapi.dtos.mapper.UserMapper;
import com.leonardolima.parkrestapi.entities.User;
import com.leonardolima.parkrestapi.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    public final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserCreateDTO user) {
        User newUser = userService.saveUser(UserMapper.toCreateUser(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toResponseUser(newUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(UserMapper.toResponseUser(user));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updatePassword(@PathVariable Long id, @RequestBody UpdatePasswordDTO user) {
        userService.updatePassword(id, user.getCurrent_password(), user.getNew_password(), user.getConfirm_new_password());
        return ResponseEntity.ok("Senha alterada com sucesso!");
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(UserMapper.toListUser(users));
    }
}
