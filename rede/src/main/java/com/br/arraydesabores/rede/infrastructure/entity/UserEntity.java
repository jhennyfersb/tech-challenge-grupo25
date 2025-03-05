package com.br.arraydesabores.rede.infrastructure.entity;

import com.br.arraydesabores.rede.domain.model.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "tb_user")
public class UserEntity extends BaseMutableEntity {

    private String name;

    private String password;

    private String email;

    private String login;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<AddressEntity> addressEntities;

//    @OneToMany(mappedBy = "user")
//    private List<RoleEntity> roles;

//    @ElementCollection(fetch = FetchType.EAGER)
//    private Set<String> roles = new HashSet<>();

}
