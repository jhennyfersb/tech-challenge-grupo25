package com.br.arraydesabores.rede.application.usecases.user;

import com.br.arraydesabores.rede.application.exception.UserNotFoundException;
import com.br.arraydesabores.rede.application.interfaces.IUserGateway;
import com.br.arraydesabores.rede.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindUserByIdUseCase {

    private final IUserGateway userRepository;

    public User execute(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(id.toString()));
    }
}
