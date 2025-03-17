package com.br.arraydesabores.rede.application.usecases.user;

import com.br.arraydesabores.rede.application.interfaces.IUserRoleGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RemoveUserRoleUseCase {
    private final IUserRoleGateway userRoleGateway;

    public void execute(Long userId, Long roleId) {
        var userRole = userRoleGateway.findByUserId(userId);
        if (userRole.stream().noneMatch(role -> Objects.equals(role.getId(), roleId))) {
            return;
        }
        userRoleGateway.removeRole(roleId);
    }
}
