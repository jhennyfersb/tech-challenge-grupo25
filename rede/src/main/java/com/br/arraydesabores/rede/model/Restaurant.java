package com.br.arraydesabores.rede.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_restaurant")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    private String address;

    // Tipo de cozinha (ex.: Italiana, Japonesa)
    private String tipoCozinha;

    // Horário de funcionamento (pode ser uma String ou outro tipo conforme a necessidade)
    private String horarioFuncionamento;

    // Associação com o dono do restaurante (usuário existente)
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
}
