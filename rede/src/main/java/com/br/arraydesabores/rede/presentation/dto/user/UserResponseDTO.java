package com.br.arraydesabores.rede.presentation.dto.user;

import com.br.arraydesabores.rede.presentation.dto.AddressDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class UserResponseDTO {
    String name;
    String login;
    String email;
    List<AddressDTO> addresses;

}
