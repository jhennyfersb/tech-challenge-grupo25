package com.br.arraydesabores.rede.application.usecases.address;

import com.br.arraydesabores.rede.application.interfaces.IAddressGateway;
import com.br.arraydesabores.rede.application.interfaces.IUserGateway;
import com.br.arraydesabores.rede.domain.model.Address;
import com.br.arraydesabores.rede.infrastructure.security.SecurityUtils;
import com.br.arraydesabores.rede.presentation.dto.address.AddressCreateDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAddressUseCase {

    private final ModelMapper modelMapper;
    private final IAddressGateway addressGateway;
    private final IUserGateway userGateway;

    public Address execute(AddressCreateDTO addressRequest) {
        var username = SecurityUtils.getCurrentUsername();
        var user = userGateway.findByUsername(username).orElse(null);

        var address = modelMapper.map(addressRequest, Address.class);
        return addressGateway.save(user, address);
    }
}
