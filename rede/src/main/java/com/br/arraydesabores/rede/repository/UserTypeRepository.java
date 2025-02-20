package com.br.arraydesabores.rede.repository;

import com.br.arraydesabores.rede.model.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTypeRepository extends JpaRepository<UserType, Long> {
}