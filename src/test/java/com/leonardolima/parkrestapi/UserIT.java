package com.leonardolima.parkrestapi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.leonardolima.parkrestapi.dtos.UserCreateDTO;
import com.leonardolima.parkrestapi.dtos.UserResponseDTO;
import com.leonardolima.parkrestapi.entities.User.Role;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "resources/sql/users/users-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "resources/sql/users/users-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserIT {

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void createUser_ValidEmailAndPassword_ReturnCreatedUserWithStatus201() {
        UserResponseDTO responseBody = webTestClient
        .post()
        .uri("/api/v1/users")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new UserCreateDTO("tody", "tody@gmail.com", "123456"))
        .exchange()
        .expectStatus().isCreated()
        .expectBody(UserResponseDTO.class)
        .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getId()).isNotNull();
        Assertions.assertThat(responseBody.getEmail()).isEqualTo("tody@gmail.com");
        Assertions.assertThat(responseBody.getRole()).isEqualTo(Role.COMMON);
    }
}
