package com.br.arraydesabores.rede.presentation.dto.user;

import com.br.arraydesabores.rede.presentation.dto.BaseResponseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserListResponse extends BaseResponseDTO {
    private String name;
    private String login;
    private String email;
}
