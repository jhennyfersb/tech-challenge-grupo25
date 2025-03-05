package com.br.arraydesabores.rede.presentation.controller;

import com.br.arraydesabores.rede.presentation.dto.AddressDTO;
import com.br.arraydesabores.rede.application.exception.UserNotFoundException;
import com.br.arraydesabores.rede.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<AddressDTO> saveAddress(@PathVariable Long userId,
                                                  @Valid @RequestBody AddressDTO addressDTO) throws UserNotFoundException {
        AddressDTO address = addressService.saveAddress(userId, addressDTO);
        return ResponseEntity.status(201).body(address);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdress(@PathVariable Long id) {
        addressService.deleteAddressById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> updateAdress(@PathVariable Long id,
                                                   @Valid @RequestBody AddressDTO addressDTO) throws UserNotFoundException {
        AddressDTO address = addressService.updateAddress(id, addressDTO);
        return ResponseEntity.status(200).body(address);
    }
}