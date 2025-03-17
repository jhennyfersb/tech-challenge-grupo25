package com.br.arraydesabores.rede.presentation.dto.user;

import com.br.arraydesabores.rede.presentation.dto.address.AddressDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


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
    List<AddressDTO> addresses;

}
