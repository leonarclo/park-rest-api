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
import com.leonardolima.parkrestapi.exceptions.ErrorMessage;
import com.leonardolima.parkrestapi.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Usuários", description = "Contém todas as operações relativas aos recursos de cadastro, edição e leitura de usuários")
@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    public final UserService userService;

    @Operation(summary = "Criar um novo usuário", description = "Controlador de criação de novos usuários", 
    responses = {
        @ApiResponse(responseCode = "201", description = "Criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))), 
        @ApiResponse(responseCode = "409", description = "Usuário email já cadastrado no sistema", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
        @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserCreateDTO user) {
        User newUser = userService.saveUser(UserMapper.toCreateUser(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toResponseUser(newUser));
    }

    @Operation(summary = "Recuperar um usuário pelo Id", description = "Controlador para recuperar um usuário pelo Id", 
    responses = {
        @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))), 
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(UserMapper.toResponseUser(user));
    }

    @Operation(summary = "Atualizar senha do usuário", description = "Controlador de atualização de senha do usuário", 
    responses = {
        @ApiResponse(responseCode = "204", description = "Senha atualizada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))), 
        @ApiResponse(responseCode = "400", description = "Senha não confere", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
        @ApiResponse(responseCode = "422", description = "Campos inválidos ou mal formatados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody UpdatePasswordDTO user) {
        userService.updatePassword(id, user.getCurrent_password(), user.getNew_password(), user.getConfirm_new_password());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar todos os usuários", description = "Controlador para gerar todos os usuários", 
    responses = {
        @ApiResponse(responseCode = "200", description = "Usuários cadastrados listados com sucesso", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserResponseDTO.class)))), 
    })
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(UserMapper.toListUser(users));
    }
}
