package com.br.arraydesabores.rede.infrastructure.gateways;

import com.br.arraydesabores.rede.application.interfaces.IUserRoleGateway;
import com.br.arraydesabores.rede.domain.model.User;
import com.br.arraydesabores.rede.domain.model.UserRole;
import com.br.arraydesabores.rede.infrastructure.entity.UserEntity;
import com.br.arraydesabores.rede.infrastructure.entity.UserRoleEntity;
import com.br.arraydesabores.rede.infrastructure.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserRoleGatewayImpl implements IUserRoleGateway {

    private final UserRoleRepository roleRepository;
    private final ModelMapper modelMapper;


    @Override
    public List<UserRole> findByUserId(Long userId) {
        return roleRepository.findByUserId(userId)
                .stream()
                .map((role) -> modelMapper.map(role, UserRole.class))
                .toList();
    }

    @Override
    public void addRoles(User user, Set<UserRole> roles) {
        roles.forEach((role) -> {
            var entity = modelMapper.map(role, UserRoleEntity.class);
            entity.setUser(modelMapper.map(user, UserEntity.class));
            roleRepository.save(entity);
        });
    }

    @Override
    public void removeRole(Long id) {
        roleRepository.deleteById(id);
    }
}
