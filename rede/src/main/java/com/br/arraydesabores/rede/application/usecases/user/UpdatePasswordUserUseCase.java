package com.br.arraydesabores.rede.application.usecases.user;

import com.br.arraydesabores.rede.application.exception.UserNotFoundException;
import com.br.arraydesabores.rede.application.interfaces.IUserGateway;
import com.br.arraydesabores.rede.domain.model.User;
import com.br.arraydesabores.rede.infrastructure.security.SecurityUtils;
import com.br.arraydesabores.rede.presentation.dto.ChangePasswordDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdatePasswordUserUseCase {

    private final IUserGateway userGateway;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public void execute(ChangePasswordDTO changePasswordDTO) {
        var user = SecurityUtils.getCurrentUserAuth();

        if (!passwordEncoder.matches(changePasswordDTO.oldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Senha incorreta");
        }

        user.setPassword(passwordEncoder.encode(changePasswordDTO.newPassword()));
        userGateway.save(modelMapper.map(user, User.class));
    }
}
