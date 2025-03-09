package com.br.arraydesabores.rede.application.usecases.user;

import com.br.arraydesabores.rede.application.interfaces.IUserGateway;
import com.br.arraydesabores.rede.domain.enums.UserRoleType;
import com.br.arraydesabores.rede.domain.model.User;
import com.br.arraydesabores.rede.domain.model.UserRole;
import com.br.arraydesabores.rede.presentation.dto.user.UserCreateDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase {

    private final ModelMapper modelMapper;
    private final IUserGateway userGateway;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User execute(UserCreateDTO userRequest) {
        User user = modelMapper.map(userRequest, User.class);

        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        return userGateway.createConsumer(user);
    }
}
