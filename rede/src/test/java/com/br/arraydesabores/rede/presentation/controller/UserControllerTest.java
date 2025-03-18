package com.br.arraydesabores.rede.presentation.controller;

import com.br.arraydesabores.rede.application.usecases.user.AssignUserRoleUseCase;
import com.br.arraydesabores.rede.application.usecases.user.CreateUserUseCase;
import com.br.arraydesabores.rede.application.usecases.user.DeleteUserUseCase;
import com.br.arraydesabores.rede.application.usecases.user.FindAllUsersUseCase;
import com.br.arraydesabores.rede.application.usecases.user.FindUserByIdUseCase;
import com.br.arraydesabores.rede.application.usecases.user.RemoveUserRoleUseCase;
import com.br.arraydesabores.rede.application.usecases.user.UpdatePasswordUserUseCase;
import com.br.arraydesabores.rede.application.usecases.user.UpdateUserUseCase;
import com.br.arraydesabores.rede.domain.model.User;
import com.br.arraydesabores.rede.presentation.dto.ChangePasswordDTO;
import com.br.arraydesabores.rede.presentation.dto.user.UserCreateDTO;
import com.br.arraydesabores.rede.presentation.dto.user.UserListDTO;
import com.br.arraydesabores.rede.presentation.dto.user.UserListResponse;
import com.br.arraydesabores.rede.presentation.dto.user.UserResponse;
import com.br.arraydesabores.rede.presentation.dto.user.UserRoleAssignRequest;
import com.br.arraydesabores.rede.presentation.dto.user.UserUpdateDTO;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CreateUserUseCase createUserUseCase;

    @Mock
    private FindUserByIdUseCase findUserByIdUseCase;

    @Mock
    private FindAllUsersUseCase findAllUsersUseCase;

    @Mock
    private UpdateUserUseCase updateUserUseCase;

    @Mock
    private DeleteUserUseCase deleteUserUseCase;

    @Mock
    private UpdatePasswordUserUseCase updatePasswordUserUseCase;

    @Mock
    private AssignUserRoleUseCase assignUserRoleUseCase;

    @Mock
    private RemoveUserRoleUseCase removeUserRoleUseCase;

    private UserCreateDTO userCreateDTO;
    private UserResponse userResponse;
    private UserUpdateDTO userUpdateDTO;
    private ChangePasswordDTO changePasswordDTO;
    private UserRoleAssignRequest userRoleAssignRequest;

    @BeforeEach
    void setUp() {
        userCreateDTO = new UserCreateDTO();
        userResponse = new UserResponse();
        userUpdateDTO = new UserUpdateDTO();
        changePasswordDTO = new ChangePasswordDTO("123456", "12345678");
        userRoleAssignRequest = new UserRoleAssignRequest();
    }


    @Test
    @DisplayName("Deve criar um usuário")
    void shouldCreateUser() {
        // Arrange
        when(createUserUseCase.execute(userCreateDTO)).thenReturn(new User());
        when(modelMapper.map(any(User.class), eq(UserResponse.class))).thenReturn(userResponse);

        // Act
        UserResponse response = userController.create(userCreateDTO);

        // Assert
        assertNotNull(response);
        assertEquals(userResponse, response);
        verify(createUserUseCase, times(1)).execute(userCreateDTO);
        verify(modelMapper, times(1)).map(any(User.class), eq(UserResponse.class));
    }

    @Test
    @DisplayName("Deve retornar lista de usuários vazia")
    void shouldReturnListOfEmptyUsers() {
        // Arrange
        Page<UserListDTO> mockPage = new PageImpl<>(Collections.emptyList());
        when(findAllUsersUseCase.execute(any(Pageable.class))).thenReturn(mockPage);

        // Act
        Page<UserListResponse> response = userController.findAll(Pageable.unpaged());

        // Assert
        assertNotNull(response);
        assertTrue(response.getContent().isEmpty());
        verify(findAllUsersUseCase, times(1)).execute(any(Pageable.class));
    }

    @Test
    @Description("Deve retornar lista de usuários")
    void shouldReturnListOfUsers() {
        // Arrange
        var user = new UserListDTO();
        user.setId(1L);

        Page<UserListDTO> mockPage = new PageImpl<>(List.of(user));
        when(findAllUsersUseCase.execute(any(Pageable.class))).thenReturn(mockPage);
        when(modelMapper.map(any(UserListDTO.class), eq(UserListResponse.class))).thenReturn(new UserListResponse());

        // Act
        Page<UserListResponse> response = userController.findAll(Pageable.unpaged());

        // Assert
        assertNotNull(response);
        assertFalse(response.getContent().isEmpty());
        verify(findAllUsersUseCase, times(1)).execute(any(Pageable.class));
        verify(modelMapper, times(1)).map(any(UserListDTO.class), eq(UserListResponse.class));

    }

    @Test
    @DisplayName("Deve retornar um usuário pelo id")
    void shouldReturnUserById() {
        // Arrange
        when(findUserByIdUseCase.execute(1L)).thenReturn(new User());
        when(modelMapper.map(any(User.class), eq(UserResponse.class))).thenReturn(userResponse);

        // Act
        UserResponse response = userController.findById(1L);

        // Assert
        assertNotNull(response);
        assertEquals(userResponse, response);
        verify(findUserByIdUseCase, times(1)).execute(1L);
        verify(modelMapper, times(1)).map(any(User.class), eq(UserResponse.class));
    }

    @Test
    @DisplayName("Deve atualizar um usuário")
    void shouldUpdateUser() {
        // Arrange
        when(updateUserUseCase.execute(1L, userUpdateDTO)).thenReturn(new User());
        when(modelMapper.map(any(User.class), eq(UserResponse.class))).thenReturn(userResponse);

        // Act
        UserResponse response = userController.update(1L, userUpdateDTO);

        // Assert
        assertNotNull(response);
        assertEquals(userResponse, response);
        verify(updateUserUseCase, times(1)).execute(1L, userUpdateDTO);
        verify(modelMapper, times(1)).map(any(User.class), eq(UserResponse.class));
    }

    @Test
    @DisplayName("Deve deletar um usuário")
    void shouldDeleteUser() {
        // Arrange
        doNothing().when(deleteUserUseCase).execute(1L);

        // Act
        userController.delete(1L);

        // Assert
        verify(deleteUserUseCase, times(1)).execute(1L);
    }

    @Test
    @DisplayName("Deve atualizar a senha de um usuário")
    void shouldUpdatePasswordUser() {
        // Arrange
        doNothing().when(updatePasswordUserUseCase).execute(changePasswordDTO);

        // Act
        ResponseEntity<String> response = userController.changePassword(changePasswordDTO);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(updatePasswordUserUseCase, times(1)).execute(changePasswordDTO);
    }

    @Test
    @DisplayName("Deve atribuir um papel a um usuário")
    void shouldAssignRoleToUser() {
        // Arrange
        doNothing().when(assignUserRoleUseCase).execute(userRoleAssignRequest);

        // Act
        ResponseEntity<Void> response = userController.assignRoles(userRoleAssignRequest);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(assignUserRoleUseCase, times(1)).execute(userRoleAssignRequest);
    }

    @Test
    @DisplayName("Deve remover um papel de um usuário")
    void shouldRemoveRoleFromUser() {
        // Arrange
        Long userId = 1L;
        Long roleId = 2L;
        doNothing().when(removeUserRoleUseCase).execute(userId, roleId);

        // Act
        ResponseEntity<Void> response = userController.removeRole(userId, roleId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(removeUserRoleUseCase, times(1)).execute(userId, roleId);
    }
}