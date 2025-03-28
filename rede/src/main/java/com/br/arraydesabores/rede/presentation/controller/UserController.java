package com.br.arraydesabores.rede.presentation.controller;

import com.br.arraydesabores.rede.application.usecases.user.AssignUserRoleUseCase;
import com.br.arraydesabores.rede.application.usecases.user.CreateUserUseCase;
import com.br.arraydesabores.rede.application.usecases.user.DeleteUserUseCase;
import com.br.arraydesabores.rede.application.usecases.user.FindAllUsersUseCase;
import com.br.arraydesabores.rede.application.usecases.user.FindUserByIdUseCase;
import com.br.arraydesabores.rede.application.usecases.user.RemoveUserRoleUseCase;
import com.br.arraydesabores.rede.application.usecases.user.UpdatePasswordUserUseCase;
import com.br.arraydesabores.rede.application.usecases.user.UpdateUserUseCase;
import com.br.arraydesabores.rede.presentation.dto.ChangePasswordDTO;
import com.br.arraydesabores.rede.presentation.dto.user.UserCreateDTO;
import com.br.arraydesabores.rede.presentation.dto.user.UserListResponse;
import com.br.arraydesabores.rede.presentation.dto.user.UserResponse;
import com.br.arraydesabores.rede.application.exception.UserNotFoundException;
import com.br.arraydesabores.rede.presentation.dto.user.UserRoleAssignRequest;
import com.br.arraydesabores.rede.presentation.dto.user.UserUpdateDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final ModelMapper modelMapper;
    private final CreateUserUseCase createUserUseCase;
    private final FindUserByIdUseCase findUserByIdUseCase;
    private final FindAllUsersUseCase findAllUsersUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final UpdatePasswordUserUseCase updatePasswordUserUseCase;
    private final AssignUserRoleUseCase assignUserRoleUseCase;
    private final RemoveUserRoleUseCase removeUserRoleUseCase;


    @PostMapping
    public UserResponse create(@RequestBody UserCreateDTO userRequest) {
        return modelMapper.map(createUserUseCase.execute(userRequest), UserResponse.class);
    }

    @GetMapping
    public Page<UserListResponse> findAll(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        return findAllUsersUseCase.execute(pageable).map(user -> modelMapper.map(user, UserListResponse.class));
    }

    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable("id") Long id) throws UserNotFoundException {
        return modelMapper.map(findUserByIdUseCase.execute(id), UserResponse.class);
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable("id") Long id,
                               @Valid @RequestBody UserUpdateDTO userDTO) throws UserNotFoundException {
        return modelMapper.map(updateUserUseCase.execute(id, userDTO), UserResponse.class);
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @Valid @RequestBody ChangePasswordDTO dto) {

        updatePasswordUserUseCase.execute(dto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        deleteUserUseCase.execute(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/assign-roles")
    public ResponseEntity<Void> assignRoles(@RequestBody @Valid UserRoleAssignRequest role) {
        assignUserRoleUseCase.execute(role);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}/roles/{roleId}")
    public ResponseEntity<Void> removeRole(@PathVariable("id") Long id, @PathVariable("roleId") Long roleId) {
        removeUserRoleUseCase.execute(id, roleId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
