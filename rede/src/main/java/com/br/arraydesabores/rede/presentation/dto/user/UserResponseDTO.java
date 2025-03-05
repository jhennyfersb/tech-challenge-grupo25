package com.br.arraydesabores.rede.presentation.dto.user;

import com.br.arraydesabores.rede.presentation.dto.address.AddressResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class UserResponseDTO {
    Long id;
    String name;
    String login;
    String email;
    List<AddressResponseDTO> addresses;

}
