package com.br.arraydesabores.rede.infrastructure.repository;

import com.br.arraydesabores.rede.infrastructure.entity.UserEntity;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long>, PagingAndSortingRepository<UserEntity, Long>, JpaRepository<UserEntity, Long> {
    @EntityGraph(attributePaths = {"roles"})
    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(@Email String email);

    Optional<UserEntity> findByLogin(String username);
}
