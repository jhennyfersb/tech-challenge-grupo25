package com.br.arraydesabores.rede.application.usecases.user;

import com.br.arraydesabores.rede.application.exception.UserNotFoundException;
import com.br.arraydesabores.rede.application.interfaces.IUserGateway;
import com.br.arraydesabores.rede.domain.model.User;
import com.br.arraydesabores.rede.presentation.dto.ChangePasswordDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdatePasswordUserUseCase {

    private final IUserGateway userGateway;
    private final PasswordEncoder passwordEncoder;

    public void execute(String username, ChangePasswordDTO changePasswordDTO) {
        User user = userGateway.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        if (!passwordEncoder.matches(changePasswordDTO.oldPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid old password");
        }

        user.setPassword(passwordEncoder.encode(changePasswordDTO.newPassword()));
        userGateway.save(user);
    }
}
