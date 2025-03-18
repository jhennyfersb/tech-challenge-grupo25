package com.br.arraydesabores.rede.application.usecases.user;

import com.br.arraydesabores.rede.application.interfaces.IUserRoleGateway;
import com.br.arraydesabores.rede.domain.enums.UserRoleType;
import com.br.arraydesabores.rede.domain.model.User;
import com.br.arraydesabores.rede.domain.model.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RemoveUserRoleUseCaseTest {

    @Mock
    private IUserRoleGateway userRoleGateway;

    @InjectMocks
    private RemoveUserRoleUseCase removeUserRoleUseCase;

    @Test
    @DisplayName("Deve remover uma role de um usuário com sucesso")
    public void shouldRemoveRoleFromUserSuccessfully() {
        Long userId = 1L;
        UserRole roleAdmin = new UserRole(1L, UserRoleType.ADMIN);

        List<UserRole> userRoleList = List.of(roleAdmin);

        when(userRoleGateway.findByUserId(1L)).thenReturn(userRoleList);

        removeUserRoleUseCase.execute(userId, 1L);

        verify(userRoleGateway, times(1)).removeRole(1L);
    }

    @Test
    @DisplayName("Deve retornar sem remover uma role de um usuário")
    public void shouldReturnWithoutRemovingRoleFromUser() {
        Long userId = 1L;
        UserRole roleAdmin = new UserRole(1L, UserRoleType.ADMIN);

        List<UserRole> userRoleList = List.of(roleAdmin);

        when(userRoleGateway.findByUserId(1L)).thenReturn(userRoleList);

        removeUserRoleUseCase.execute(userId, 2L);

        verify(userRoleGateway, times(0)).removeRole(2L);
    }

}