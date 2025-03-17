package com.br.arraydesabores.rede.application.usecases.address;

import com.br.arraydesabores.rede.application.interfaces.IAddressGateway;
import com.br.arraydesabores.rede.application.interfaces.IUserGateway;
import com.br.arraydesabores.rede.domain.model.Address;
import com.br.arraydesabores.rede.domain.model.User;
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

    public Address execute(AddressCreateDTO addressRequest) {
        var userAuth = SecurityUtils.getCurrentUserAuth();

        var address = modelMapper.map(addressRequest, Address.class);
        return addressGateway.save(modelMapper.map(userAuth, User.class), address);
    }
}
