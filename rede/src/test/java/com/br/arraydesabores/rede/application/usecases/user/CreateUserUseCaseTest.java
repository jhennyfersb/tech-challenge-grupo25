package com.br.arraydesabores.rede.application.usecases.user;


import com.br.arraydesabores.rede.application.interfaces.IUserGateway;
import com.br.arraydesabores.rede.domain.model.User;
import com.br.arraydesabores.rede.presentation.dto.user.UserCreateDTO;
import jdk.jfr.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CreateUserUseCaseTest {

    @Mock
    private IUserGateway userGateway;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CreateUserUseCase createUserUseCase;

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
    @DisplayName("Deve criar um usuário com sucesso")
    public void shouldCreateUserSuccessfully() {
        // Arrange
        UserCreateDTO userRequest = new UserCreateDTO();
        userRequest.setName("Vinicius Norberto");
        userRequest.setEmail("vinicius.norberto@gmail.com");
        userRequest.setPassword("password123");

        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());

        when(modelMapper.map(userRequest, User.class)).thenReturn(user);
        when(passwordEncoder.encode(userRequest.getPassword())).thenReturn("encodedPassword");
        when(userGateway.createConsumer(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        User createdUser = createUserUseCase.execute(userRequest);

        // Assert
        assertNotNull(createdUser);
        assertEquals("Vinicius Norberto", createdUser.getName());
        assertEquals("vinicius.norberto@gmail.com", createdUser.getEmail());
        assertEquals("encodedPassword", createdUser.getPassword());
        verify(userGateway, times(1)).createConsumer(any(User.class));
    }

    @Test
    @DisplayName("Deve falhar ao criar um usuário com email já existente")
    public void shouldFailToCreateUserWithExistingEmail() {
        // Arrange
        UserCreateDTO userRequest = new UserCreateDTO();
        userRequest.setName("Vinicius Norberto");
        userRequest.setEmail("vinicius.norberto@gmail.com");
        userRequest.setPassword("password123");

        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());

        when(modelMapper.map(userRequest, User.class)).thenReturn(user);
        when(passwordEncoder.encode(userRequest.getPassword())).thenReturn("encodedPassword");
        when(userGateway.createConsumer(any(User.class))).thenThrow(new RuntimeException("Email already exists"));

        // Act
        try {
            createUserUseCase.execute(userRequest);
        } catch (Exception e) {
            // Assert
            assertEquals("Email already exists", e.getMessage());
            verify(userGateway, times(1)).createConsumer(any(User.class));
        }
    }

    @Test
    @DisplayName("Deve falhar ao criar um usuário com email inválido")
    public void shouldFailToCreateUserWithInvalidEmail() {
        // Arrange
        UserCreateDTO userRequest = new UserCreateDTO();
        userRequest.setName("Vinicius Norberto");
        userRequest.setEmail("vinicius.norberto");
        userRequest.setPassword("password123");

        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());

        when(modelMapper.map(userRequest, User.class)).thenReturn(user);
        when(passwordEncoder.encode(userRequest.getPassword())).thenReturn("encodedPassword");
        when(userGateway.createConsumer(any(User.class))).thenThrow(new RuntimeException("Email inválido"));

        // Act
        try {
            createUserUseCase.execute(userRequest);
        } catch (Exception e) {
            // Assert
            assertEquals("Email inválido", e.getMessage());
            verify(userGateway, times(1)).createConsumer(any(User.class));
        }
    }

    @Test
    @DisplayName("Deve falhar ao criar um usuário com senha inválida")
    public void shouldFailToCreateUserWithInvalidPassword() {
        // Arrange
        UserCreateDTO userRequest = new UserCreateDTO();
        userRequest.setName("Vinicius Norberto");
        userRequest.setEmail("vinicius.norberto@gmail.com");
        userRequest.setPassword("123");

        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());

        when(modelMapper.map(userRequest, User.class)).thenReturn(user);
        when(passwordEncoder.encode(userRequest.getPassword())).thenReturn("encodedPassword");
        when(userGateway.createConsumer(any(User.class))).thenThrow(new RuntimeException("Senha deve ter entre 6 e 20 caracteres"));

        // Act
        try {
            createUserUseCase.execute(userRequest);
        } catch (Exception e) {
            // Assert
            assertEquals("Senha deve ter entre 6 e 20 caracteres", e.getMessage());
            verify(userGateway, times(1)).createConsumer(any(User.class));
        }
    }

}
