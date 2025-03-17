package com.br.arraydesabores.rede.infrastructure.gateways;

import com.br.arraydesabores.rede.application.exception.AddressNotFoundException;
import com.br.arraydesabores.rede.domain.model.Address;
import com.br.arraydesabores.rede.domain.model.User;
import com.br.arraydesabores.rede.infrastructure.entity.AddressEntity;
import com.br.arraydesabores.rede.infrastructure.entity.UserEntity;
import com.br.arraydesabores.rede.infrastructure.repository.AddressRepository;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AddressGatewayImplTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressGatewayImpl addressGateway;

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
    @DisplayName("Deve salvar endereço com sucesso")
    void shouldSaveAddressSuccessfully() {
        // Arrange
        User userDomain = new User();
        userDomain.setName("Vinicius Norberto");
        userDomain.setEmail("vinicius.norberto@gmail.com");

        UserEntity userEntity = new UserEntity();
        userEntity.setName(userDomain.getName());
        userEntity.setEmail(userDomain.getEmail());

        Address address = new Address();
        address.setStreet("Rua dos Bobos");
        address.setNumber(123);
        address.setCity("São Paulo");

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setStreet(address.getStreet());
        addressEntity.setNumber(address.getNumber());
        addressEntity.setCity(address.getCity());
        addressEntity.setUser(userEntity);


        when(modelMapper.map(address, AddressEntity.class)).thenReturn(addressEntity);
        when(modelMapper.map(userDomain, UserEntity.class)).thenReturn(userEntity);
        when(addressRepository.save(addressEntity)).thenReturn(addressEntity);
        when(modelMapper.map(addressEntity, Address.class)).thenReturn(address);

        // Act
        Address addressSaved = addressGateway.save(userDomain, address);

        // Assert
        assertNotNull(addressSaved);
        assertEquals("São Paulo", addressSaved.getCity());
        verify(addressRepository, times(1)).save(any(AddressEntity.class));
    }

    @Test
    @DisplayName("Deve buscar endereço por id e id do usuário")
    void shouldFindAddressByIdAndUserId() {
        // Arrange
        Long id = 1L;
        Long userId = 1L;

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(id);
        addressEntity.setStreet("Rua dos Bobos");
        addressEntity.setNumber(123);
        addressEntity.setCity("São Paulo");

        Address address = new Address();
        address.setId(id);
        address.setStreet("Rua dos Bobos");
        address.setNumber(123);
        address.setCity("São Paulo");

        when(addressRepository.findByIdAndUserId(id, userId)).thenReturn(java.util.Optional.of(addressEntity));
        when(modelMapper.map(addressEntity, Address.class)).thenReturn(address);

        // Act
        Address addressFound = addressGateway.findByIdAndUserId(id, userId);

        // Assert
        assertNotNull(addressFound);
        assertEquals("São Paulo", addressFound.getCity());
        verify(addressRepository, times(1)).findByIdAndUserId(id, userId);
    }

    @Test
    @DisplayName("Deve causar exceção ao buscar endereço por id e id do usuário")
    void shouldThrowExceptionWhenFindAddressByIdAndUserId() {
        // Arrange
        Long id = 1L;
        Long userId = 1L;

        when(addressRepository.findByIdAndUserId(id, userId)).thenReturn(java.util.Optional.empty());

        // Act and Assert
        assertThrows(AddressNotFoundException.class, () -> addressGateway.findByIdAndUserId(id, userId));
        verify(addressRepository, times(1)).findByIdAndUserId(id, userId);
    }

}