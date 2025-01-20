package com.br.arraydesabores.rede.service;

import com.br.arraydesabores.rede.dto.AddressDTO;
import com.br.arraydesabores.rede.exception.AddressNotFoundException;
import com.br.arraydesabores.rede.exception.UserNotFoundException;
import com.br.arraydesabores.rede.model.Address;
import com.br.arraydesabores.rede.model.User;
import com.br.arraydesabores.rede.repository.AddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public AddressService(AddressRepository addressRepository, ModelMapper modelMapper, UserService userService) {
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    public AddressDTO saveAddress(Long userId, AddressDTO addressDTO) throws UserNotFoundException {
        var address = modelMapper.map(addressDTO, Address.class);
        User user = userService.findById(userId);
        address.setUser(user);
        return modelMapper.map(addressRepository.save(address), AddressDTO.class);
    }

    public void deleteAddressById(Long id) {
        addressRepository.findById(id);
        addressRepository.deleteById(id);
    }

    public AddressDTO updateAddress(Long addressId, AddressDTO addressDTO) throws AddressNotFoundException, UserNotFoundException {
        Address existingAddress = findAddressById(addressId);
        if (Objects.nonNull(addressDTO.getUserId())) {
            existingAddress.setUser(userService.findById(addressDTO.getUserId()));
        }
        modelMapper.map(addressDTO, existingAddress);
        Address updatedAddress = addressRepository.save(existingAddress);
        return modelMapper.map(updatedAddress, AddressDTO.class);
    }

    public Address findAddressById(Long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new AddressNotFoundException("Address not found with ID " + addressId));
    }
}

