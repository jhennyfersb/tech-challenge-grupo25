package com.br.arraydesabores.rede.infrastructure.repository;

import com.br.arraydesabores.rede.domain.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long>, PagingAndSortingRepository<Address, Long>, JpaRepository<Address, Long> {
}
