package com.br.arraydesabores.rede.model;



import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "tb_user")
public class User extends DomainMain implements Serializable {

    private String name;

    private String password;

    private String email;

    private String login;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Address> addresses;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles = new HashSet<>();

}
