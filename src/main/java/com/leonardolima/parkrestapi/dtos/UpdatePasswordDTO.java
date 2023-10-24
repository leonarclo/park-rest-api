package com.leonardolima.parkrestapi.dtos;

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
    private String current_password;
    private String new_password;
    private String confirm_new_password;    
}
