package com.br.arraydesabores.rede.application.interfaces;

import com.br.arraydesabores.rede.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IUserGateway {
    public User save(User user);

    public Page<User> findAll(Pageable pageable);

    public Optional<User> findById(Long id);

    public void deleteById(Long id);

    public User update(User user);

    Optional<User> findByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
