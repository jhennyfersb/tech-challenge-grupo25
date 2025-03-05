package com.br.arraydesabores.rede.domain.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class User extends DomainMain implements Serializable {

    private String name;
    private String password;
    private String email;
    private String login;
    private List<Address> addresses = new ArrayList<>();
    private Set<String> roles = new HashSet<>();

}
