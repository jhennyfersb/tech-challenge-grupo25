package com.br.arraydesabores.rede.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tb_user_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    // Relacionamento para visualizar os usuários deste tipo.
    @OneToMany(mappedBy = "userType", cascade = CascadeType.ALL)
    private List<User> users;
}