package com.br.arraydesabores.rede.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@RequiredArgsConstructor
public class UserResponseDTO implements Serializable {
    public String name;
    public String login;
    public String email;
    public List<String> roles;
    public List<AddressDTO> addressDTO;
}
