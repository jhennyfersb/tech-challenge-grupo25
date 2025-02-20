package com.br.arraydesabores.rede.service;

import com.br.arraydesabores.rede.model.UserType;
import com.br.arraydesabores.rede.repository.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserTypeService {

    @Autowired
    private UserTypeRepository userTypeRepository;

    /**
     * Retorna todos os tipos de usuário.
     */
    public List<UserType> getAll() {
        return userTypeRepository.findAll();
    }

    /**
     * Busca um UserType pelo ID.
     */
    public Optional<UserType> getById(Long id) {
        return userTypeRepository.findById(id);
    }

    /**
     * Salva ou atualiza um UserType.
     */
    public UserType save(UserType userType) {
        return userTypeRepository.save(userType);
    }

    /**
     * Remove um UserType pelo ID.
     */
    public void delete(Long id) {
        userTypeRepository.deleteById(id);
    }
}
