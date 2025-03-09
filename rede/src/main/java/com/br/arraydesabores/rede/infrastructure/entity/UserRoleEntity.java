package com.br.arraydesabores.rede.infrastructure.entity;

import com.br.arraydesabores.rede.domain.enums.UserRoleType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "tb_user_role")
@RequiredArgsConstructor
public class UserRoleEntity extends BaseImmutableEntity {

    @Enumerated(EnumType.STRING)
    private UserRoleType role;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public UserRoleEntity(UserRoleType role) {
        super();
        this.role = role;
    }
}
