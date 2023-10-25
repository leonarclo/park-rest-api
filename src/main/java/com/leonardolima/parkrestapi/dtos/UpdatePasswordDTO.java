package com.leonardolima.parkrestapi.dtos;

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
public class UpdatePasswordDTO {
    @NotBlank
    @Size(min = 6, message = "A senha precisa ter no mínimo 6 caracteres.")
    private String current_password;
    @NotBlank
    @Size(min = 6, message = "A senha precisa ter no mínimo 6 caracteres.")
    private String new_password;
    @NotBlank
    @Size(min = 6, message = "A senha precisa ter no mínimo 6 caracteres.")
    private String confirm_new_password;    
}
