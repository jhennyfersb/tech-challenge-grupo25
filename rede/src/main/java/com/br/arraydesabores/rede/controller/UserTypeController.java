package com.br.arraydesabores.rede.controller;

import com.br.arraydesabores.rede.model.UserType;
import com.br.arraydesabores.rede.service.UserTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-types")
public class UserTypeController {

    private final UserTypeService userTypeService;

    public UserTypeController(UserTypeService userTypeService) {
        this.userTypeService = userTypeService;
    }

    @GetMapping
    public List<UserType> getAll() {
        return userTypeService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserType> getById(@PathVariable Long id) {
        return userTypeService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public UserType create(@RequestBody UserType userType) {
        return userTypeService.save(userType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserType> update(@PathVariable Long id, @RequestBody UserType updatedUserType) {
        return userTypeService.getById(id)
                .map(existing -> {
                    existing.setName(updatedUserType.getName());
                    return ResponseEntity.ok(userTypeService.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userTypeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
