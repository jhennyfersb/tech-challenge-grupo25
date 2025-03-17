package com.br.arraydesabores.rede.application.usecases.user;

import com.br.arraydesabores.rede.application.interfaces.IUserGateway;
import com.br.arraydesabores.rede.domain.model.User;
import com.br.arraydesabores.rede.presentation.dto.user.UserUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUserUseCase {

    private final ModelMapper modelMapper;
    private final IUserGateway userGateway;
    private final FindUserByIdUseCase findUserByIdUseCase;

    public User execute(Long id, UserUpdateDTO userRequest) {
        User userBase = findUserByIdUseCase.execute(id);
        modelMapper.map(userRequest, userBase);
        return userGateway.update(userBase);
    }
}
