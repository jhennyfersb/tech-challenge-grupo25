package com.br.arraydesabores.rede.repository;

import com.br.arraydesabores.rede.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long>, PagingAndSortingRepository<User, Long>, JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
