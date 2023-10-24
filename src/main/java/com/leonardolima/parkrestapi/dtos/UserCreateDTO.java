package com.leonardolima.parkrestapi.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class UserCreateDTO {


    @NotBlank(message = "O nome não pode estar em branco.")
    @Size(min = 3, message = "O nome precisa ter no mínimo 3 letras.")
    private String name;

    @NotBlank(message = "O email não pode estar em branco.")
    @Email(message = "Formato do email inválido!")
    private String email;

    @NotBlank(message = "A senha não pode estar em branco.")
    @Size(min = 6, message = "A senha precisa ter no mínimo 6 caracteres.")
    private String password;
}
