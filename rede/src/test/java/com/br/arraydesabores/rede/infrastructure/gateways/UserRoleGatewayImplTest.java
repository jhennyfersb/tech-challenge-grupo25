package com.br.arraydesabores.rede.infrastructure.gateways;

import com.br.arraydesabores.rede.domain.enums.UserRoleType;
import com.br.arraydesabores.rede.domain.model.User;
import com.br.arraydesabores.rede.domain.model.UserRole;
import com.br.arraydesabores.rede.infrastructure.entity.UserRoleEntity;
import com.br.arraydesabores.rede.infrastructure.repository.UserRepository;
import com.br.arraydesabores.rede.infrastructure.repository.UserRoleRepository;
import jdk.jfr.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserRoleGatewayImplTest {
    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserRoleRepository userRoleRepository;

    @InjectMocks
    private UserRoleGatewayImpl userRoleGateway;

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
    @Description("Deve retornar uma lista de roles de um usuário")
    void shouldReturnAListOfRolesFromAUser() {
        Long userId = 1L;
        var userRoleENtity = new UserRoleEntity(UserRoleType.USER);
        var userRole = new UserRole(1L, UserRoleType.USER);
        when(userRoleRepository.findByUserId(userId)).thenReturn(List.of(userRoleENtity));
        when(modelMapper.map(userRole, UserRole.class)).thenReturn(userRole);

        var roles = userRoleGateway.findByUserId(userId);

        assertNotNull(roles);
        assertEquals(1, roles.size());
    }

    @Test
    @Description("Deve adicionar roles a um usuário")
    void shouldAddRolesToAUser() {
        var user = new User();
        var userRole = new UserRole(1L, UserRoleType.USER);
        var userRoleEntity = new UserRoleEntity(UserRoleType.USER);
        var roles = Set.of(userRole);
        when(modelMapper.map(userRole, UserRoleEntity.class)).thenReturn(userRoleEntity);
        when(userRoleRepository.save(userRoleEntity)).thenReturn(userRoleEntity);

        userRoleGateway.addRoles(user, roles);

        verify(userRoleRepository, times(1)).save(userRoleEntity);
    }

    @Test
    @Description("Deve remover uma role de um usuário")
    void shouldRemoveARoleFromAUser() {
        Long id = 1L;
        doNothing().when(userRoleRepository).deleteById(id);

        userRoleGateway.removeRole(id);

        verify(userRoleRepository, times(1)).deleteById(id);
    }

}