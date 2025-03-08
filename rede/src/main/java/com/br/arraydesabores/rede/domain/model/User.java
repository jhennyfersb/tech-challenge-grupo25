package com.br.arraydesabores.rede.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class User extends DomainMain implements Serializable {

    private String name;
    private String password;
    private String email;
    private String login;
    private List<Address> addresses;
    private Set<UserRole> roles;

}
