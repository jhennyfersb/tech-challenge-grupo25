package com.br.arraydesabores.rede.application.usecases.user;

import com.br.arraydesabores.rede.application.interfaces.IUserGateway;
import com.br.arraydesabores.rede.domain.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DeleteUserUseCaseTest {

    @Mock
    private IUserGateway userGateway;

    @Mock
    private FindUserByIdUseCase findUserByIdUseCase;

    @InjectMocks
    private DeleteUserUseCase deleteUserUseCase;

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
    @DisplayName("Deve deletar um usuário com sucesso")
    public void shouldDeleteUserSuccessfully() {
        // Arrange
        Long id = 1L;
        User user = new User();
        user.setName("Vinicius Norberto");
        user.setEmail("vinicius.norberto@gmail.com");
        user.setId(id);

        when(findUserByIdUseCase.execute(id)).thenReturn(user);

        // Act
        deleteUserUseCase.execute(id);

        // Assert
        verify(userGateway, times(1)).deleteById(id);
    }
}