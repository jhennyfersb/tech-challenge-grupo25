package com.br.arraydesabores.rede.infrastructure.service;

import com.br.arraydesabores.rede.domain.model.User;
import com.br.arraydesabores.rede.infrastructure.entity.UserEntity;
import com.br.arraydesabores.rede.infrastructure.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Description;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

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
    @Description("Deve retornar um usuário")
    void shouldReturnAUser() {
        String username = "username";
        var userEntity = new UserEntity();
        var user = new User(1L);
        user.setEmail("username@email.com");
        user.setPassword("senha123");

        when(repository.findByEmail(username)).thenReturn(Optional.of(userEntity));
        when(modelMapper.map(userEntity, User.class)).thenReturn(user);

        var userDetails = customUserDetailsService.loadUserByUsername(username);

        assertNotNull(userDetails);
    }

    @Test
    @Description("Deve retornar um erro ao não encontrar um usuário")
    void shouldReturnAnErrorWhenUserNotFound() {
        String username = "username";
        when(repository.findByEmail(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername(username));
    }

}