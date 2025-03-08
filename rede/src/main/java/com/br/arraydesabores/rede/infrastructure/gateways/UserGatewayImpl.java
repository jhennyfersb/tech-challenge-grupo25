package com.br.arraydesabores.rede.infrastructure.gateways;

import com.br.arraydesabores.rede.application.exception.UserNotFoundException;
import com.br.arraydesabores.rede.application.interfaces.IUserGateway;
import com.br.arraydesabores.rede.domain.model.User;
import com.br.arraydesabores.rede.infrastructure.entity.UserEntity;
import com.br.arraydesabores.rede.infrastructure.repository.UserRepository;
import com.br.arraydesabores.rede.infrastructure.repository.UserRoleRepository;
import com.br.arraydesabores.rede.presentation.dto.user.UserListDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserGatewayImpl implements IUserGateway {

    private final UserRepository repository;
    private final UserRoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Override
    public User save(User user) {
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        userEntity = repository.save(userEntity);
        return modelMapper.map(userEntity, User.class);
    }

    @Override
    public Page<UserListDTO> findAll(Pageable pageable) {
        Page<UserEntity> userEntities = repository.findAll(pageable);
        return userEntities.map(entity -> modelMapper.map(entity, UserListDTO.class));
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return repository.findById(id)
                .map((entity) -> modelMapper.map(entity, User.class))
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public User update(User user) {
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        userEntity = repository.save(userEntity);
        return modelMapper.map(userEntity, User.class);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        var userEntity = repository.findByLogin(username);
        return userEntity.map(entity -> modelMapper.map(entity, User.class));
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email)
                .map(entity -> modelMapper.map(entity, User.class));
    }

}
