package com.br.arraydesabores.rede.application.interfaces;

import com.br.arraydesabores.rede.domain.model.Address;
import com.br.arraydesabores.rede.domain.model.User;

public interface IAddressGateway {
    public Address save(User user, Address address);

    public Address findByIdAndUserId(Long id, Long userId);

    public void deleteById(Long id);

}
