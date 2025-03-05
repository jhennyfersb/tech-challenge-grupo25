package com.br.arraydesabores.rede.service;

import com.br.arraydesabores.rede.application.exception.AddressNotFoundException;
import com.br.arraydesabores.rede.application.exception.UserNotFoundException;
import com.br.arraydesabores.rede.domain.model.Address;
import com.br.arraydesabores.rede.domain.model.User;
import com.br.arraydesabores.rede.infrastructure.entity.AddressEntity;
import com.br.arraydesabores.rede.infrastructure.repository.AddressRepository;
import com.br.arraydesabores.rede.presentation.dto.address.AddressDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public AddressDTO saveAddress(Long userId, AddressDTO addressDTO) throws UserNotFoundException {
        var address = modelMapper.map(addressDTO, Address.class);
        User user = userService.findById(userId);
        return modelMapper.map(addressRepository.save(modelMapper.map(address, AddressEntity.class)), AddressDTO.class);
    }

    public void deleteAddressById(Long id) {
        addressRepository.findById(id);
        addressRepository.deleteById(id);
    }

    public AddressDTO updateAddress(Long addressId, AddressDTO addressDTO) throws AddressNotFoundException, UserNotFoundException {
        Address existingAddress = findAddressById(addressId);
//        if (Objects.nonNull(addressDTO.getUserId())) {
//            existingAddress.setUser(userService.findById(addressDTO.getUserId()));
//        }

        existingAddress.setUpdatedAt(LocalDateTime.now());
        modelMapper.map(addressDTO, existingAddress);
        AddressEntity updatedAddress = addressRepository.save(modelMapper.map(existingAddress, AddressEntity.class));

        return modelMapper.map(updatedAddress, AddressDTO.class);
    }

    public Address findAddressById(Long addressId) {
        return addressRepository.findById(addressId)
                .map(address -> modelMapper.map(address, Address.class))
                .orElseThrow(() -> new AddressNotFoundException("Address not found with ID " + addressId));
    }
}

