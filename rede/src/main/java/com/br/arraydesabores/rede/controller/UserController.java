package com.br.arraydesabores.rede.controller;

import com.br.arraydesabores.rede.dto.ChangePasswordDTO;
import com.br.arraydesabores.rede.dto.UserDTO;
import com.br.arraydesabores.rede.dto.UserResponseDTO;
import com.br.arraydesabores.rede.model.User;
import com.br.arraydesabores.rede.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping
    public UserResponseDTO create(@RequestBody UserDTO userDTO) {
        return modelMapper.map(userService.create(userDTO), UserResponseDTO.class);
    }

    @GetMapping("/all")
    public Page<UserResponseDTO> findAll(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        return userService.findAll(pageable)
                .map(user -> modelMapper.map(user, UserResponseDTO.class));
    }

    @GetMapping("/{id}")
    public UserResponseDTO findById(@PathVariable("id") Long id) {
        return modelMapper.map(userService.findById(id), UserResponseDTO.class);
    }

    @PutMapping("/{id}")
    public UserResponseDTO update(@PathVariable("id") Long id,
                       @RequestBody UserDTO userDTO) {
        return modelMapper.map(userService.update(id, userDTO), UserResponseDTO.class);
    }

    @PutMapping("/{id}/change-password")
    public ResponseEntity<String> changePassword(@PathVariable Long id,
                                                 @RequestBody ChangePasswordDTO changePasswordDTO) {
        try {
            userService.changePassword(id, changePasswordDTO.oldPassword(), changePasswordDTO.newPassword());
            return ResponseEntity.ok("Senha alterada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
