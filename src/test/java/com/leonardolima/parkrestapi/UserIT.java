package com.leonardolima.parkrestapi;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.leonardolima.parkrestapi.dtos.UpdatePasswordDTO;
import com.leonardolima.parkrestapi.dtos.UserCreateDTO;
import com.leonardolima.parkrestapi.dtos.UserResponseDTO;
import com.leonardolima.parkrestapi.entities.User.Role;
import com.leonardolima.parkrestapi.exceptions.ErrorMessage;

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


    @Test
    public void createUser_InvalidEmail_ReturnErrorMessageStatus422() {
        ErrorMessage responseBody = webTestClient
        .post()
        .uri("/api/v1/users")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new UserCreateDTO("tody", "", "123456"))
        .exchange()
        .expectStatus().isEqualTo(422)
        .expectBody(ErrorMessage.class)
        .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);


        responseBody = webTestClient
        .post()
        .uri("/api/v1/users")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new UserCreateDTO("tody", "tody", "123456"))
        .exchange()
        .expectStatus().isEqualTo(422)
        .expectBody(ErrorMessage.class)
        .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);


        responseBody = webTestClient
        .post()
        .uri("/api/v1/users")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new UserCreateDTO("tody", "tody@", "123456"))
        .exchange()
        .expectStatus().isEqualTo(422)
        .expectBody(ErrorMessage.class)
        .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);


        responseBody = webTestClient
        .post()
        .uri("/api/v1/users")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new UserCreateDTO("tody", "tody@gmail", "123456"))
        .exchange()
        .expectStatus().isEqualTo(422)
        .expectBody(ErrorMessage.class)
        .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
        

        responseBody = webTestClient
        .post()
        .uri("/api/v1/users")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new UserCreateDTO("tody", "tody@gmail.", "123456"))
        .exchange()
        .expectStatus().isEqualTo(422)
        .expectBody(ErrorMessage.class)
        .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);


        responseBody = webTestClient
        .post()
        .uri("/api/v1/users")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new UserCreateDTO("tody", "tody@gmail.c", "123456"))
        .exchange()
        .expectStatus().isEqualTo(422)
        .expectBody(ErrorMessage.class)
        .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }


    @Test
    public void createUser_InvalidPassword_ReturnErrorMessageStatus422() {
        ErrorMessage responseBody = webTestClient
        .post()
        .uri("/api/v1/users")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new UserCreateDTO("tody", "tody@gmail.com", ""))
        .exchange()
        .expectStatus().isEqualTo(422)
        .expectBody(ErrorMessage.class)
        .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = webTestClient
        .post()
        .uri("/api/v1/users")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new UserCreateDTO("tody", "tody@gmail.com", "12345"))
        .exchange()
        .expectStatus().isEqualTo(422)
        .expectBody(ErrorMessage.class)
        .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }


    @Test
    public void createUser_ExistingEmail_ReturnErrorMessageStatus409() {
        ErrorMessage responseBody = webTestClient
        .post()
        .uri("/api/v1/users")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new UserCreateDTO("tody", "ana@gmail.com", "123456"))
        .exchange()
        .expectStatus().isEqualTo(409)
        .expectBody(ErrorMessage.class)
        .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
    }


    @Test
    public void getUserbyId_ExistingId_ReturnUserWithStatus200() {
        UserResponseDTO responseBody = webTestClient
        .get()
        .uri("/api/v1/users/100")
        .exchange()
        .expectStatus().isOk()
        .expectBody(UserResponseDTO.class)
        .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getId()).isEqualTo(100);
        Assertions.assertThat(responseBody.getEmail()).isEqualTo("ana@gmail.com");
        Assertions.assertThat(responseBody.getRole()).isEqualTo(Role.COMMON);
    }


    @Test
    public void getUserbyId_IdDoesNotExist_ReturnErrorMessageWithStatus404() {
        ErrorMessage responseBody = webTestClient
        .get()
        .uri("/api/v1/users/0")
        .exchange()
        .expectStatus().isNotFound()
        .expectBody(ErrorMessage.class)
        .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }


    @Test
    public void updatePassword_ValidPasswordAndNewPassword_ReturnUpdatedPasswordWithStatus204() {
        webTestClient
        .patch()
        .uri("/api/v1/users/100")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new UpdatePasswordDTO("123456", "123456", "123456"))
        .exchange()
        .expectStatus().isNoContent();
    }


    @Test
    public void updatePassword_IdDoesNotExist_ReturnErrorMessageWithStatus404() {
        ErrorMessage responseBody = webTestClient
        .patch()
        .uri("/api/v1/users/0")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new UpdatePasswordDTO("123456", "123456", "123456"))
        .exchange()
        .expectStatus().isNotFound()
        .expectBody(ErrorMessage.class)
        .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }


    @Test
    public void updatePassword_InvalidOrNullPasswords_ReturnErrorMessageWithStatus422() {
        ErrorMessage responseBody = webTestClient
        .patch()
        .uri("/api/v1/users/100")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new UpdatePasswordDTO("", "", ""))
        .exchange()
        .expectStatus().isEqualTo(422)
        .expectBody(ErrorMessage.class)
        .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = webTestClient
        .patch()
        .uri("/api/v1/users/100")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new UpdatePasswordDTO("123456", "12345", "12345"))
        .exchange()
        .expectStatus().isEqualTo(422)
        .expectBody(ErrorMessage.class)
        .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }


    @Test
    public void updatePassword_PasswordsDoesNotMatch_ReturnErrorMessageWithStatus400() {
        ErrorMessage responseBody = webTestClient
        .patch()
        .uri("/api/v1/users/100")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new UpdatePasswordDTO("123456", "000000", "123456"))
        .exchange()
        .expectStatus().isEqualTo(400)
        .expectBody(ErrorMessage.class)
        .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);


        responseBody = webTestClient
        .patch()
        .uri("/api/v1/users/100")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new UpdatePasswordDTO("000000", "123456", "123456"))
        .exchange()
        .expectStatus().isEqualTo(400)
        .expectBody(ErrorMessage.class)
        .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);
    }


    @Test
    public void getUserList_ReturnUserListWithStatus200() {
        List<UserResponseDTO> responseBody = webTestClient
        .get()
        .uri("/api/v1/users")
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(UserResponseDTO.class)
        .returnResult().getResponseBody();

        Assertions.assertThat(responseBody).isNotNull();
        Assertions.assertThat(responseBody.size()).isEqualTo(3);
    }
}
