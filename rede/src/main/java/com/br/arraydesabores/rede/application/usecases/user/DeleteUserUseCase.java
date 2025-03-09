package com.br.arraydesabores.rede.application.usecases.user;

import com.br.arraydesabores.rede.application.interfaces.IUserGateway;
import com.br.arraydesabores.rede.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserUseCase {

    private final IUserGateway userGateway;
    private final FindUserByIdUseCase findUserByIdUseCase;

    public void execute(Long id) {
        User userBase = findUserByIdUseCase.execute(id);
        userGateway.deleteById(userBase.getId());
    }
}
