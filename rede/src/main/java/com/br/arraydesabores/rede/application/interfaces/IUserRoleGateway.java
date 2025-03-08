package com.br.arraydesabores.rede.application.interfaces;

import com.br.arraydesabores.rede.domain.model.User;
import com.br.arraydesabores.rede.domain.model.UserRole;

import java.util.List;
import java.util.Set;

public interface IUserRoleGateway {

    List<UserRole> findByUserId(Long userId);

    void addRoles(User user, Set<UserRole> roles);

    void removeRole(Long id);
}
