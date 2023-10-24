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

import com.leonardolima.parkrestapi.dtos.UserCreateDTO;
import com.leonardolima.parkrestapi.dtos.UserResponseDTO;
import com.leonardolima.parkrestapi.dtos.mapper.UserMapper;
import com.leonardolima.parkrestapi.entities.User;
import com.leonardolima.parkrestapi.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    public final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreateDTO user) {
        User newUser = userService.saveUser(UserMapper.toUser(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDTO(newUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updatePassword(@PathVariable Long id, @RequestBody User user) {
        User getUser = userService.updatePassword(id, user.getPassword());
        return ResponseEntity.ok(getUser);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
