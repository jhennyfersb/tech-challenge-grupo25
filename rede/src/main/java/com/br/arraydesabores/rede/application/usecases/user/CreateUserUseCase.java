package com.br.arraydesabores.rede.application.usecases.user;

import com.br.arraydesabores.rede.application.interfaces.IUserGateway;
import com.br.arraydesabores.rede.domain.model.User;
import com.br.arraydesabores.rede.presentation.dto.user.UserCreateDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase {

    private final ModelMapper modelMapper;
    private final IUserGateway userGateway;

    public User execute(UserCreateDTO userRequest) {
        User user = modelMapper.map(userRequest, User.class);
        User userSaved = userGateway.save(user);
        return modelMapper.map(userSaved, User.class);
    }
}
