package com.br.arraydesabores.rede.application.usecases.address;

import com.br.arraydesabores.rede.application.exception.AddressNotFoundException;
import com.br.arraydesabores.rede.application.interfaces.IAddressGateway;
import com.br.arraydesabores.rede.application.interfaces.IUserGateway;
import com.br.arraydesabores.rede.infrastructure.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteAddressUseCase {

    private final IAddressGateway addressGateway;
    private final IUserGateway userGateway;

    public void execute(Long id) {
        var username = SecurityUtils.getCurrentUsername();
        var user = userGateway.findByUsername(username).orElse(null);

        if (user != null && user.getAddresses().stream().noneMatch(address -> address.getId().equals(id))) {
            throw new AddressNotFoundException("Address not found");
        }

        addressGateway.deleteById(id);
    }
}
