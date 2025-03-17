package com.br.arraydesabores.rede.application.usecases.address;

import com.br.arraydesabores.rede.application.interfaces.IAddressGateway;
import com.br.arraydesabores.rede.domain.model.Address;
import com.br.arraydesabores.rede.presentation.dto.user.UserAuthDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DeleteAddressUseCaseTest {
    @Mock
    private IAddressGateway addressGateway;

    @InjectMocks
    private DeleteAddressUseCase deleteAddressUseCase;

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
    @DisplayName("Deve deletar um endereço com sucesso")
    void shouldDeleteAddressSuccessfully() {
        // Arrange
        Long id = 1L;
        Address address = new Address();
        address.setId(id);

        UserAuthDTO userAuth = new UserAuthDTO();
        userAuth.setId(1L);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userAuth, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(addressGateway.findByIdAndUserId(id, 1L)).thenReturn(address);

        // Act
        deleteAddressUseCase.execute(id);

        // Assert
        verify(addressGateway, times(1)).deleteById(id);
    }

}