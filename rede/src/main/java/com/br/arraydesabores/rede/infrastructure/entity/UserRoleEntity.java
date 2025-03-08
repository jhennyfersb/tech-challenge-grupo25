package com.br.arraydesabores.rede.infrastructure.entity;

import com.br.arraydesabores.rede.domain.enums.UserRoleEnum;
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
@AllArgsConstructor
public class UserRoleEntity extends BaseImmutableEntity {

    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private UserEntity user;
}
