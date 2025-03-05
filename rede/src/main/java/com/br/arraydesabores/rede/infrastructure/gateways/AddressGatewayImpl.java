package com.br.arraydesabores.rede.infrastructure.gateways;

import com.br.arraydesabores.rede.application.interfaces.IAddressGateway;
import com.br.arraydesabores.rede.domain.model.Address;
import com.br.arraydesabores.rede.infrastructure.entity.AddressEntity;
import com.br.arraydesabores.rede.infrastructure.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressGatewayImpl implements IAddressGateway {

    private final AddressRepository repository;
    private final ModelMapper modelMapper;


    @Override
    public Address save(Address address) {
        var addressSaved = repository.save(modelMapper.map(address, AddressEntity.class));
        return modelMapper.map(addressSaved, Address.class);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
