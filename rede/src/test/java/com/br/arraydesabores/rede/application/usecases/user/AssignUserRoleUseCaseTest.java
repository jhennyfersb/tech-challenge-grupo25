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
        Long userId = 1L;
        UserRole roleAdmin = new UserRole(1L, UserRoleType.ADMIN);
        UserRole roleUser = new UserRole(2L, UserRoleType.USER);

        User user = new User();
        user.setId(userId);
        var userRoles = new HashSet<UserRole>();
        userRoles.add(roleAdmin);
        user.setRoles(userRoles);

        UserRoleAssignRequest request = new UserRoleAssignRequest();
        request.setUserId(userId);
        var setRoles = new HashSet<UserRole>();
        setRoles.add(roleUser);
        setRoles.add(roleAdmin);
        request.setRoles(setRoles);

        when(userGateway.findById(userId)).thenReturn(user);

        assignUserRoleUseCase.execute(request);

        verify(userGateway, times(1)).findById(userId);
        verify(userRoleGateway, times(1)).addRoles(user, Set.of(roleUser));

    }

    @Test
    @DisplayName("Deve encerrar o metodo quando não houver novas roles para atribuir")
    public void shouldEndMethodWhenThereAreNoNewRolesToAssign() {
        Long userId = 1L;
        UserRole roleAdmin = new UserRole(1L, UserRoleType.ADMIN);

        User user = new User();
        user.setId(userId);
        var userRoles = new HashSet<UserRole>();
        userRoles.add(roleAdmin);
        user.setRoles(userRoles);

        UserRoleAssignRequest request = new UserRoleAssignRequest();
        request.setUserId(userId);
        var setRoles = new HashSet<UserRole>();
        setRoles.add(roleAdmin);
        request.setRoles(setRoles);

        when(userGateway.findById(userId)).thenReturn(user);

        assignUserRoleUseCase.execute(request);

        verify(userGateway, times(1)).findById(userId);
        verify(userRoleGateway, times(0)).addRoles(user, setRoles);
    }

}