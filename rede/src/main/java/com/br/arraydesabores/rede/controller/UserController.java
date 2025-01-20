package com.br.arraydesabores.rede.controller;

import com.br.arraydesabores.rede.dto.ChangePasswordDTO;
import com.br.arraydesabores.rede.dto.UserDTO;
import com.br.arraydesabores.rede.exception.UserNotFoundException;
import com.br.arraydesabores.rede.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    public UserController(UserService userService,
                          ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public UserDTO create(@RequestBody UserDTO userDTO) {
        return userService.create(userDTO);
    }

    @GetMapping("/users")
    public Page<UserDTO> findAll(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        return userService.findAll(pageable).map(user -> modelMapper.map(user, UserDTO.class));
    }

    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable("id") Long id) throws UserNotFoundException {
        return modelMapper.map(userService.findById(id), UserDTO.class);
    }

    @PutMapping("/{id}")
    public UserDTO update(@PathVariable("id") Long id,
                          @Valid @RequestBody UserDTO userDTO) throws UserNotFoundException {
        return userService.update(id, userDTO);
    }

    @PutMapping("/{id}/change-password")
    public ResponseEntity<String> changePassword(@PathVariable Long id,
                                                 @Valid @RequestBody ChangePasswordDTO changePasswordDTO) {
        try {
            userService.changePassword(id, changePasswordDTO.oldPassword(), changePasswordDTO.newPassword());
            return ResponseEntity.ok("Password changed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
