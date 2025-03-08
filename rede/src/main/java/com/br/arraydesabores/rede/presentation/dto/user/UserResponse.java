package com.br.arraydesabores.rede.presentation.dto.user;

import com.br.arraydesabores.rede.domain.model.UserRole;
import com.br.arraydesabores.rede.presentation.dto.BaseResponseDTO;
import com.br.arraydesabores.rede.presentation.dto.address.AddressResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserResponse extends BaseResponseDTO {
    String name;
    String login;
    String email;
    List<AddressResponseDTO> addresses;
    Set<UserRole> roles;
}
