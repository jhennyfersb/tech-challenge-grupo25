package com.br.arraydesabores.rede.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class User extends DomainMain {

    private String name;
    private String password;
    private String email;
    private String login;
    private List<Address> addresses;
    private Set<UserRole> roles = new HashSet<>();
    private List<Restaurant> restaurants;

}
