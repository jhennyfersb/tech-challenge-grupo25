package com.br.arraydesabores.rede.application.usecases.user;

import com.br.arraydesabores.rede.application.interfaces.IUserGateway;
import com.br.arraydesabores.rede.application.interfaces.IUserRoleGateway;
import com.br.arraydesabores.rede.domain.enums.UserRoleType;
import com.br.arraydesabores.rede.domain.model.User;
import com.br.arraydesabores.rede.domain.model.UserRole;
import com.br.arraydesabores.rede.presentation.dto.user.UserRoleAssignRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AssignUserRoleUseCaseTest {

    @Mock
    private IUserGateway userGateway;

    @Mock
    private IUserRoleGateway userRoleGateway;

    @InjectMocks
    private AssignUserRoleUseCase assignUserRoleUseCase;

    AutoCloseable mock;

    @BeforeEach
    public void setUp() {
        mock = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void releaseMocks() throws Exception {
        mock.close();
    }

    @Test
    @DisplayName("Deve atribuir uma role para um usuário com sucesso")
    public void shouldAssignRoleToUserSuccessfully() {
        // Arrange
        UserRoleAssignRequest request = new UserRoleAssignRequest();
        request.setUserId(1L);
        var roleAdmin = new UserRole();
        roleAdmin.setRole(UserRoleType.ADMIN);
        var roles = new HashSet<UserRole>();
        roles.add(roleAdmin);
        request.setRoles(roles);

        User user = new User();
        user.setId(1L);
        user.setRoles(Set.of(roleAdmin));

        roleAdmin.setUser(user);

        when(userGateway.findById(1L)).thenReturn(user);
        doNothing().when(userRoleGateway).addRoles(user, roles);

        // Act
        assignUserRoleUseCase.execute(request);

        // Assert
        verify(userRoleGateway, times(0)).addRoles(user, roles);
    }

}