package com.br.arraydesabores.rede.application.interfaces;

import com.br.arraydesabores.rede.domain.model.Address;

public interface IAddressGateway {
    public Address save(Address user);

    public void deleteById(Long id);

}
