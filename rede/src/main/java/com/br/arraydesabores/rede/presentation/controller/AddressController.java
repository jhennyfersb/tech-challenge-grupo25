package com.br.arraydesabores.rede.presentation.controller;

import com.br.arraydesabores.rede.application.usecases.address.CreateAddressUseCase;
import com.br.arraydesabores.rede.application.usecases.address.DeleteAddressUseCase;
import com.br.arraydesabores.rede.presentation.dto.address.AddressCreateDTO;
import com.br.arraydesabores.rede.presentation.dto.address.AddressResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final ModelMapper modelMapper;
    private final CreateAddressUseCase createAddressUseCase;
    private final DeleteAddressUseCase deleteAddressUseCase;


    @PostMapping
    public ResponseEntity<AddressResponseDTO> saveAddress(@Valid @RequestBody AddressCreateDTO addressDTO) {
        var address = createAddressUseCase.execute(addressDTO);
        return ResponseEntity.status(201).body(modelMapper.map(address, AddressResponseDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        deleteAddressUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}