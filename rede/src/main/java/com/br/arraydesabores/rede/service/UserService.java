package com.br.arraydesabores.rede.service;

import com.br.arraydesabores.rede.dto.RegisterRequestDTO;
import com.br.arraydesabores.rede.dto.UserDTO;
import com.br.arraydesabores.rede.enums.Role;
import com.br.arraydesabores.rede.model.User;
import com.br.arraydesabores.rede.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User create(UserDTO userDTO) {
         var user = modelMapper.map(userDTO, User.class);
         return userRepository.save(user);
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public User update(Long id, UserDTO userDTO) {
        var user = userRepository.findById(id).orElseThrow();
        modelMapper.map(userDTO, user);
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public void changePassword(Long id, String oldPassword, String newPassword) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(oldPassword,user.getPassword())) {
            throw new RuntimeException("Senha antiga inválida");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

    }

}
