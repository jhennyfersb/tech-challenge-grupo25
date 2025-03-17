package com.br.arraydesabores.rede.application.usecases.user;

import com.br.arraydesabores.rede.application.interfaces.IUserGateway;
import com.br.arraydesabores.rede.domain.model.User;
import com.br.arraydesabores.rede.presentation.dto.user.UserListDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class FindAllUsersUseCaseTest {
    @Mock
    private IUserGateway userGateway;

    @InjectMocks
    private FindAllUsersUseCase findAllUsersUseCase;

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
    @DisplayName("Deve retornar uma lista de usuarios páginado")
    public void shouldReturnAListOfUsersPaginated() {
        // Arrange
        UserListDTO user1 = new UserListDTO();
        user1.setId(1L);
        user1.setName("Vinicius Norberto");
        user1.setEmail("vinicius.norberto@gmail.com");

        UserListDTO user2 = new UserListDTO();
        user2.setId(2L);
        user2.setName("Vinicius Pereira");
        user2.setEmail("vinicius.Pereira@hotmail.com");

        Page<UserListDTO> users = new PageImpl<UserListDTO>(List.of(user1, user2));
        when(userGateway.findAll(any(Pageable.class))).thenReturn(users);

        // Act
        Pageable pageable = PageRequest.of(0, 10);
        Page<UserListDTO> usersPage = findAllUsersUseCase.execute(pageable);

        // Assert
        assertNotNull(usersPage);
        assertEquals(2, usersPage.getContent().size());
    }

}