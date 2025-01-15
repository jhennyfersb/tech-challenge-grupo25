package com.br.arraydesabores.rede.controller;

import com.br.arraydesabores.rede.dto.ChangePasswordDTO;
import com.br.arraydesabores.rede.dto.UserDTO;
import com.br.arraydesabores.rede.model.User;
import com.br.arraydesabores.rede.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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

    @PostMapping
    public User create(@RequestBody UserDTO userDTO) {
        return userService.create(userDTO);
    }

    @GetMapping("/users")
    public Page<User> findAll(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        return userService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @GetMapping
    public ResponseEntity<?> getUser(@PathVariable("id") Long id){
        return ResponseEntity.ok(userService.findById(id));
    }

    @PutMapping("/{id}")
    public User update(@PathVariable("id") Long id,
                       @Valid @RequestBody UserDTO userDTO) {
        return userService.update(id, userDTO);
    }

    @PutMapping("/{id}/change-password")
    public ResponseEntity<String> changePassword(@PathVariable Long id,
                                                 @Valid @RequestBody ChangePasswordDTO changePasswordDTO) {
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

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
