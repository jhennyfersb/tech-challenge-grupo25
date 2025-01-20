package com.br.arraydesabores.rede.repository;

import com.br.arraydesabores.rede.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AddressRepository extends CrudRepository<Address, Long>, PagingAndSortingRepository<Address, Long>, JpaRepository<Address, Long> {
}
