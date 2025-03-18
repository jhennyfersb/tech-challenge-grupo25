package com.br.arraydesabores.rede.presentation.controller;

import com.br.arraydesabores.rede.application.usecases.address.CreateAddressUseCase;
import com.br.arraydesabores.rede.application.usecases.address.DeleteAddressUseCase;
import com.br.arraydesabores.rede.domain.model.Address;
import com.br.arraydesabores.rede.presentation.dto.address.AddressCreateDTO;
import com.br.arraydesabores.rede.presentation.dto.address.AddressResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressControllerTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CreateAddressUseCase createAddressUseCase;

    @Mock
    private DeleteAddressUseCase deleteAddressUseCase;

    @InjectMocks
    private AddressController addressController;

    @Test
    @DisplayName("Deve salvar um endereço")
    void shouldSaveAddress() {
        // Arrange
        var addressCreateDTO = AddressCreateDTO.builder()
                .street("Rua 1")
                .number(123)
                .city("Cidade 1")
                .build();

        var address = new Address();
        address.setId(1L);
        address.setStreet("Rua 1");
        address.setNumber(123);
        address.setCity("Cidade 1");

        var addressResponseDTO = new AddressResponseDTO();
        addressResponseDTO.setId(1L);
        addressResponseDTO.setStreet("Rua 1");
        addressResponseDTO.setNumber(123);
        addressResponseDTO.setCity("Cidade 1");

        when(createAddressUseCase.execute(addressCreateDTO)).thenReturn(address);
        when(modelMapper.map(address, AddressResponseDTO.class)).thenReturn(addressResponseDTO);

        when(createAddressUseCase.execute(addressCreateDTO)).thenReturn(address);
        when(modelMapper.map(address, AddressResponseDTO.class)).thenReturn(addressResponseDTO);

        // Act
        var response = addressController.saveAddress(addressCreateDTO);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Rua 1", response.getBody().getStreet());
        assertEquals(123, response.getBody().getNumber());
        assertEquals("Cidade 1", response.getBody().getCity());
    }

    @Test
    @DisplayName("Deve deletar um endereço")
    void shouldDeleteAddress() {
        // Arrange
        var id = 1L;

        // Act
        var response = addressController.deleteAddress(id);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

}