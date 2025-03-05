package com.br.arraydesabores.rede.infrastructure.repository;

import com.br.arraydesabores.rede.infrastructure.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<AddressEntity, Long>, PagingAndSortingRepository<AddressEntity, Long>, JpaRepository<AddressEntity, Long> {
}
