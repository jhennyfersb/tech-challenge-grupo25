package com.br.arraydesabores.rede.service;

import com.br.arraydesabores.rede.application.exception.UserNotFoundException;
import com.br.arraydesabores.rede.domain.model.User;
import com.br.arraydesabores.rede.infrastructure.entity.UserEntity;
import com.br.arraydesabores.rede.infrastructure.repository.UserRepository;
import com.br.arraydesabores.rede.presentation.dto.user.UserDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(ModelMapper modelMapper,
                       UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(user -> modelMapper.map(user, User.class));
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .map(user -> modelMapper.map(user, User.class))
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Transactional
    public UserDTO update(Long id, UserDTO userDTO) throws UserNotFoundException {
        var user = findById(id);
        modelMapper.map(userDTO, user);
        return modelMapper.map(userRepository.save(modelMapper.map(user, UserEntity.class)), UserDTO.class);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public void changePassword(Long id, String oldPassword, String newPassword) throws UserNotFoundException {
        User user = findById(id);

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Invalid old password");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(modelMapper.map(user, UserEntity.class));

    }

    public boolean existsByEmail(@Email String email) {
        return userRepository.existsByEmail(email);
    }

    public UserDTO findByEmail(@Email String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return modelMapper.map(user, UserDTO.class);
    }
}
