package com.br.arraydesabores.rede.infrastructure.entity;

import com.br.arraydesabores.rede.domain.enums.UserRoleType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
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
public class UserEntity extends BaseMutableEntity {

    private String name;

    private String password;

    private String email;

    private String login;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<AddressEntity> addresses;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<UserRoleEntity> roles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<RestaurantEntity> restaurants = new ArrayList<>();

    public void addRole(UserRoleType roleType) {
        UserRoleEntity role = new UserRoleEntity(roleType);
        role.setUser(this);

        if (this.roles == null) {
            this.roles = new HashSet<>();
        }
        this.roles.add(role);
    }

}
