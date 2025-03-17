package com.br.arraydesabores.rede.application.usecases.user;

import com.br.arraydesabores.rede.application.interfaces.IUserGateway;
import com.br.arraydesabores.rede.domain.model.User;
import com.br.arraydesabores.rede.presentation.dto.user.UserUpdateDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UpdateUserUseCaseTest {

    @Mock
    private IUserGateway userGateway;

    @Mock
    private FindUserByIdUseCase findUserByIdUseCase;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UpdateUserUseCase updateUserUseCase;

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
    @DisplayName("Deve atualizar usuário com sucesso")
    void shouldUpdateUserSuccessfully() {
        // Arrange
        Long userId = 1L;

        UserUpdateDTO userRequest = new UserUpdateDTO();
        userRequest.setName("Vinicius Atualizado");
        userRequest.setEmail("vinicius.norberto@gmail.com");

        User userBase = new User();
        userBase.setId(userId);
        userBase.setName("Vinicius Norberto");
        userBase.setEmail("vinicius.norberto@gmail.com");

        User userUpdate = new User();
        userUpdate.setId(userId);
        userUpdate.setName("Vinicius Atualizado");
        userUpdate.setEmail("vinicius.norberto@gmail.com");

        when(findUserByIdUseCase.execute(userId)).thenReturn(userBase);
        doNothing().when(modelMapper).map(userRequest, userBase);
        when(userGateway.update(userBase)).thenReturn(userUpdate);

        // Act
        var userUpdated = updateUserUseCase.execute(userId, userRequest);

        // Assert
        assertNotNull(userUpdated);
        assertEquals("Vinicius Atualizado", userUpdated.getName());
        assertEquals("vinicius.norberto@gmail.com", userUpdated.getEmail());
        verify(userGateway, times(1)).update(any(User.class));
    }

}