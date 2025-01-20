package com.br.arraydesabores.rede.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    String name;
    String login;
    @Size(min = 6, max = 20)
    String password;
    @Email
    String email;

}
