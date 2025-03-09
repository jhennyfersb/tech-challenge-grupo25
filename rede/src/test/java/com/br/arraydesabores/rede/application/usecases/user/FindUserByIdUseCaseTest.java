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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class FindUserByIdUseCaseTest {
    @Mock
    private IUserGateway userGateway;

    @InjectMocks
    private FindUserByIdUseCase findUserByIdUseCase;

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
    @DisplayName("Deve retornar um usuario por id")
    public void shouldReturnAUserById() {
        // Arrange
        User user1 = new User();
        user1.setId(1L);
        user1.setName("Vinicius Norberto");
        user1.setEmail("vinicius.norberto@gmail.com");

        when(userGateway.findById(1L)).thenReturn(user1);

        // Act
        var user = findUserByIdUseCase.execute(1L);

        // Assert
        assertNotNull(user);
        assertEquals(user.getName(), user1.getName());
        assertEquals(user.getEmail(), user1.getEmail());
    }

}