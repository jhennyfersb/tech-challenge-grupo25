package com.br.arraydesabores.rede.application.usecases.address;

import com.br.arraydesabores.rede.application.interfaces.IAddressGateway;
import com.br.arraydesabores.rede.domain.model.Address;
import com.br.arraydesabores.rede.domain.model.User;
import com.br.arraydesabores.rede.presentation.dto.address.AddressCreateDTO;
import com.br.arraydesabores.rede.presentation.dto.user.UserAuthDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateAddressUseCaseTest {
    @Mock
    private IAddressGateway addressGateway;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CreateAddressUseCase createAddressUseCase;

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
    @DisplayName("Deve criar um endereço com sucesso")
    void shouldCreateAddressSuccessfully() {
        // Arrange
        User userDomain = new User();
        userDomain.setName("Vinicius Norberto");

        AddressCreateDTO addressCreateDTO = AddressCreateDTO.builder().street("Rua 1").number(123).city("São Paulo").build();

        Address addressDomain = new Address();
        addressDomain.setStreet(addressCreateDTO.getStreet());
        addressDomain.setNumber(addressCreateDTO.getNumber());
        addressDomain.setCity(addressCreateDTO.getCity());

        UserAuthDTO userAuth = new UserAuthDTO();
        userAuth.setId(1L);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userAuth, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(modelMapper.map(addressCreateDTO, Address.class)).thenReturn(addressDomain);
        when(modelMapper.map(userAuth, User.class)).thenReturn(userDomain);
        when(addressGateway.save(userDomain, addressDomain)).thenReturn(addressDomain);

        // Act
        Address addressSaved = createAddressUseCase.execute(addressCreateDTO);

        // Assert
        assertNotNull(addressSaved);
        assertEquals("São Paulo", addressSaved.getCity());
    }

}