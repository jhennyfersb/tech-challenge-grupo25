package com.br.arraydesabores.rede.application.usecases.user;

import com.br.arraydesabores.rede.application.interfaces.IUserGateway;
import com.br.arraydesabores.rede.application.interfaces.IUserRoleGateway;
import com.br.arraydesabores.rede.domain.model.UserRole;
import com.br.arraydesabores.rede.presentation.dto.user.UserRoleAssignRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AssignUserRoleUseCase {
    private final IUserGateway userGateway;
    private final IUserRoleGateway userRoleGateway;

    public void execute(UserRoleAssignRequest request) {
        var user = userGateway.findById(request.getUserId());

        Set<UserRole> existingRoles = Objects.nonNull(user.getRoles()) ? user.getRoles() : Set.of();

        request.getRoles().removeAll(existingRoles);
        if (request.getRoles().isEmpty()) return;

        userRoleGateway.addRoles(user, request.getRoles());
    }
}
